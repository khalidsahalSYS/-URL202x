package com.url202x.socialapp

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val navigationIds = listOf(R.id.nav_home, R.id.nav_friends, R.id.nav_notifications, R.id.nav_menu)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationIds.forEach { id -> findViewById<View>(id).setOnClickListener { selectNavigation(id) } }
        findViewById<View>(R.id.create_post).setOnClickListener { showCreatePostDialog() }
        findViewById<View>(R.id.post_action_like).setOnClickListener { toggleLike(it as TextView) }
        findViewById<View>(R.id.post_action_comment).setOnClickListener { showCommentDialog() }
        findViewById<View>(R.id.post_action_share).setOnClickListener {
            Toast.makeText(this, getString(R.string.share_coming_soon), Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.search_button).setOnClickListener { showSearchDialog() }
        findViewById<View>(R.id.messages_button).setOnClickListener {
            Toast.makeText(this, getString(R.string.messages_coming_soon), Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.photo_action).setOnClickListener {
            Toast.makeText(this, getString(R.string.photo_coming_soon), Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.checkin_action).setOnClickListener {
            Toast.makeText(this, getString(R.string.checkin_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectNavigation(selectedId: Int) {
        navigationIds.forEach { id ->
            findViewById<TextView>(id).setTextColor(color(if (id == selectedId) R.color.brand else R.color.muted))
        }
        if (selectedId != R.id.nav_home) {
            Toast.makeText(this, getString(R.string.navigation_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSearchDialog() {
        val input = EditText(this).apply {
            hint = getString(R.string.search_hint)
            setSingleLine(true)
            setPadding(dp(16), dp(12), dp(16), dp(12))
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.search)
            .setView(input)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.search) { _, _ ->
                Toast.makeText(this, getString(R.string.search_coming_soon), Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun showCreatePostDialog() {
        val input = EditText(this).apply {
            hint = getString(R.string.new_post_hint)
            minLines = 3
            gravity = Gravity.TOP or Gravity.START
            setPadding(dp(16), dp(12), dp(16), dp(12))
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.create_post_title)
            .setView(input)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.publish) { _, _ ->
                val postText = input.text.toString().trim()
                if (postText.isEmpty()) {
                    Toast.makeText(this, getString(R.string.empty_post), Toast.LENGTH_SHORT).show()
                } else {
                    addPost(postText)
                }
            }
            .show()
    }

    private fun addPost(text: String) {
        val feed = findViewById<LinearLayout>(R.id.feed_posts)
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(14), dp(14), dp(14), dp(8))
            setBackgroundColor(color(R.color.surface))
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = dp(8) }
        }
        val author = TextView(this).apply {
            text = getString(R.string.your_name) + " · " + getString(R.string.just_now)
            textSize = 14f
            setTextColor(color(R.color.muted))
        }
        val content = TextView(this).apply {
            this.text = text
            textSize = 16f
            setTextColor(color(R.color.text_primary))
            setPadding(0, dp(12), 0, dp(12))
        }
        val like = TextView(this).apply {
            this.text = "♡  " + getString(R.string.like)
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(color(R.color.muted))
            setPadding(dp(8), dp(10), dp(8), dp(10))
            setOnClickListener { toggleLike(this) }
        }
        card.addView(author)
        card.addView(content)
        card.addView(like)
        feed.addView(card, 0)
        findViewById<ScrollView>(R.id.feed_scroll).smoothScrollTo(0, 0)
        Toast.makeText(this, getString(R.string.post_published), Toast.LENGTH_SHORT).show()
    }

    private fun showCommentDialog() {
        val input = EditText(this).apply {
            hint = getString(R.string.comment_hint)
            setPadding(dp(16), dp(12), dp(16), dp(12))
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.add_comment)
            .setView(input)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.send) { _, _ ->
                if (input.text.toString().trim().isNotEmpty()) {
                    Toast.makeText(this, getString(R.string.comment_saved_demo), Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    private fun toggleLike(action: TextView) {
        val liked = action.tag == "liked"
        action.tag = if (liked) "unliked" else "liked"
        action.setTextColor(color(if (liked) R.color.muted else R.color.brand))
        action.text = (if (liked) "♡  " else "♥  ") + getString(if (liked) R.string.like else R.string.liked)
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    @Suppress("DEPRECATION")
    private fun color(colorId: Int): Int = if (Build.VERSION.SDK_INT >= 23) {
        getColor(colorId)
    } else {
        resources.getColor(colorId)
    }
}
