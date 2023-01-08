package kg.news.newsapi.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.toast(txt: String) = Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
fun Fragment.toast(txt: String) = Toast.makeText(this.context, txt, Toast.LENGTH_SHORT).show()