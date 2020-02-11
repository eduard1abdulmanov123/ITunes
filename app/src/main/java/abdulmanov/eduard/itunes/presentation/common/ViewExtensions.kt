package abdulmanov.eduard.itunes.presentation.common

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.reactivex.Observable

fun ViewGroup.inflate(layoutId:Int, attachToRoot:Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun View.visibilityGone(show:Boolean){
    visibility = if(show) View.VISIBLE else View.GONE
}

fun View.visibilityInvisible(show:Boolean){
    visibility = if(show) View.VISIBLE else View.INVISIBLE
}

fun ImageView.loadImg(imageUrl: String, placeholderRes: Int? = null) {
    Picasso.get().load(imageUrl).apply {
        placeholderRes?.let {placeholder(it) }
        fit().into(this@loadImg)
    }
}

fun EditText.focus(show:Boolean){
    if(show){
        requestFocus()
        context.showKeyboard()
    }else{
        clearFocus()
        context.hideKeyboard(this)
    }
}

fun EditText.createObservableChange():Observable<String>{
    return Observable.create<String> {emitter->
        addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { emitter.onNext(it.toString()) }
            }
        })
    }
}