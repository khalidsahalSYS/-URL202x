package com.khalidsahalsys.url202x;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int INK = Color.rgb(27, 39, 51);
    private static final int MUTED = Color.rgb(111, 125, 140);
    private static final int BLUE = Color.rgb(39, 111, 214);
    private static final int SURFACE = Color.WHITE;
    private LinearLayout feed;
    private EditText composer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(244, 247, 250));
        getWindow().setNavigationBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(createScreen());
        addPostCard("سارة أحمد", "من الجميل أن نبدأ يومنا بفكرة جديدة ومشاركة بسيطة مع الأصدقاء. 🌿", "قبل ١٠ دقائق", 12);
        addPostCard("محمد علي", "أهلًا بالجميع! هذا مجتمع URL202x — مساحة خفيفة للتواصل ومشاركة اللحظات.", "قبل ساعة", 8);
        addPostCard("ليلى حسن", "نبحث دائمًا عن طرق تجعل التواصل أسرع وأسهل، حتى مع اتصال إنترنت ضعيف. ✨", "أمس", 21);
    }

    private View createScreen() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        root.setBackgroundColor(Color.rgb(244, 247, 250));

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(dp(20), dp(12), dp(20), dp(12));
        header.setBackgroundColor(SURFACE);
        TextView brand = text("URL202x", 22, INK, true);
        brand.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        header.addView(brand, new LinearLayout.LayoutParams(0, dp(48), 1));
        TextView avatar = text("م", 18, Color.WHITE, true);
        avatar.setGravity(Gravity.CENTER);
        avatar.setBackground(circle(BLUE));
        header.addView(avatar, new LinearLayout.LayoutParams(dp(42), dp(42)));
        root.addView(header);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(16), dp(16), dp(24));
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        TextView title = text("آخر المنشورات", 21, INK, true);
        title.setPadding(dp(2), dp(4), dp(2), dp(12));
        content.addView(title);

        LinearLayout composeCard = card();
        composeCard.setPadding(dp(14), dp(12), dp(14), dp(12));
        composer = new EditText(this);
        composer.setHint("بماذا تفكر؟ شارك منشورًا...");
        composer.setTextSize(15);
        composer.setTextColor(INK);
        composer.setHintTextColor(MUTED);
        composer.setGravity(Gravity.TOP | Gravity.RIGHT);
        composer.setMinLines(2);
        composer.setMaxLines(4);
        composer.setPadding(dp(8), dp(8), dp(8), dp(8));
        composer.setBackgroundColor(Color.TRANSPARENT);
        composer.setTextDirection(View.TEXT_DIRECTION_FIRST_STRONG_RTL);
        composeCard.addView(composer, new LinearLayout.LayoutParams(-1, -2));
        Button publish = button("نشر المنشور");
        LinearLayout.LayoutParams publishParams = new LinearLayout.LayoutParams(-1, dp(46));
        publishParams.topMargin = dp(8);
        composeCard.addView(publish, publishParams);
        publish.setOnClickListener(v -> publishPost());
        content.addView(composeCard);

        TextView feedTitle = text("من المجتمع", 17, INK, true);
        feedTitle.setPadding(dp(2), dp(22), dp(2), dp(10));
        content.addView(feedTitle);
        feed = new LinearLayout(this);
        feed.setOrientation(LinearLayout.VERTICAL);
        content.addView(feed);
        return root;
    }

    private void publishPost() {
        String message = composer.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            composer.setError("اكتب شيئًا قبل النشر");
            return;
        }
        addPostCard("أنت", message, "الآن", 0);
        composer.setText("");
        composer.clearFocus();
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (keyboard != null) keyboard.hideSoftInputFromWindow(composer.getWindowToken(), 0);
    }

    private void addPostCard(String author, String message, String time, int initialLikes) {
        LinearLayout post = card();
        post.setOrientation(LinearLayout.VERTICAL);
        post.setPadding(dp(15), dp(14), dp(15), dp(12));
        LinearLayout.LayoutParams postParams = new LinearLayout.LayoutParams(-1, -2);
        postParams.bottomMargin = dp(12);
        feed.addView(post, 0, postParams);

        LinearLayout byline = new LinearLayout(this);
        byline.setGravity(Gravity.CENTER_VERTICAL);
        TextView badge = text(author.substring(0, 1), 16, BLUE, true);
        badge.setGravity(Gravity.CENTER);
        badge.setBackground(circle(Color.rgb(232, 241, 252)));
        byline.addView(badge, new LinearLayout.LayoutParams(dp(40), dp(40)));
        LinearLayout identity = new LinearLayout(this);
        identity.setOrientation(LinearLayout.VERTICAL);
        identity.setPadding(dp(10), 0, 0, 0);
        identity.addView(text(author, 15, INK, true));
        TextView timestamp = text(time, 12, MUTED, false);
        LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(-2, -2);
        timeParams.topMargin = dp(2);
        identity.addView(timestamp, timeParams);
        byline.addView(identity, new LinearLayout.LayoutParams(0, -2, 1));
        post.addView(byline);

        TextView body = text(message, 15, INK, false);
        body.setGravity(Gravity.RIGHT);
        body.setTextDirection(View.TEXT_DIRECTION_FIRST_STRONG_RTL);
        body.setLineSpacing(dp(3), 1f);
        body.setPadding(0, dp(14), 0, dp(14));
        post.addView(body);

        View divider = new View(this);
        divider.setBackgroundColor(Color.rgb(237, 240, 244));
        post.addView(divider, new LinearLayout.LayoutParams(-1, dp(1)));
        final int[] likes = {initialLikes};
        final boolean[] liked = {false};
        Button like = new Button(this);
        like.setAllCaps(false);
        like.setTextColor(MUTED);
        like.setTextSize(14);
        like.setBackgroundTintList(ColorStateList.valueOf(SURFACE));
        like.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        like.setText("♡  إعجاب  ·  " + likes[0]);
        like.setOnClickListener(v -> {
            liked[0] = !liked[0];
            likes[0] += liked[0] ? 1 : -1;
            like.setTextColor(liked[0] ? BLUE : MUTED);
            like.setText((liked[0] ? "♥" : "♡") + "  إعجاب  ·  " + likes[0]);
        });
        post.addView(like, new LinearLayout.LayoutParams(-1, dp(42)));
    }

    private LinearLayout card() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackground(roundRect(SURFACE, dp(16)));
        layout.setElevation(dp(1));
        return layout;
    }

    private Button button(String label) {
        Button button = new Button(this);
        button.setText(label);
        button.setTextSize(15);
        button.setTextColor(Color.WHITE);
        button.setAllCaps(false);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setBackground(roundRect(BLUE, dp(12)));
        return button;
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        if (bold) view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        view.setGravity(Gravity.RIGHT);
        view.setTextDirection(View.TEXT_DIRECTION_FIRST_STRONG_RTL);
        return view;
    }

    private GradientDrawable roundRect(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    private GradientDrawable circle(int color) {
        GradientDrawable drawable = roundRect(color, dp(50));
        drawable.setShape(GradientDrawable.OVAL);
        return drawable;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
