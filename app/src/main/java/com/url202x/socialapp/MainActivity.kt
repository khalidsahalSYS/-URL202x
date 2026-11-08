package com.url202x.socialapp

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val navigationIds = listOf(R.id.nav_home, R.id.nav_friends, R.id.nav_notifications, R.id.nav_menu)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationIds.forEach { id ->
            findViewById<View>(id).setOnClickListener { selectNavigation(id) }
        }
        findViewById<View>(R.id.create_post).setOnClickListener {
            Toast.makeText(this, getString(R.string.post_coming_soon), Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.post_action_like).setOnClickListener { toggleLike(it as TextView) }
        findViewById<View>(R.id.post_action_comment).setOnClickListener {
            Toast.makeText(this, getString(R.string.comments_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectNavigation(selectedId: Int) {
        navigationIds.forEach { id ->
            findViewById<TextView>(id).setTextColor(getColorCompat(if (id == selectedId) R.color.brand else R.color.muted))
        }
        if (selectedId != R.id.nav_home) {
            Toast.makeText(this, getString(R.string.navigation_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleLike(action: TextView) {
        val liked = action.tag == "liked"
        action.tag = if (liked) "unliked" else "liked"
        action.setTextColor(getColorCompat(if (liked) R.color.muted else R.color.brand))
        action.text = getString(if (liked) R.string.like else R.string.liked)
    }

    @Suppress("DEPRECATION")
    private fun getColorCompat(colorId: Int): Int = getColor(colorId)
}
