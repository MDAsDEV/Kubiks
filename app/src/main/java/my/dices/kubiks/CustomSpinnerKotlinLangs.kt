package my.dices.kubiks

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

internal class SpinnerAdapterLangs(context: Context, var colorsList: List<String>) :
    ArrayAdapter<String?>(context, R.layout.my_selected_items, colorsList) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.my_dropdown_item, parent, false)
        val color = row.findViewById<TextView>(R.id.text)
        val color_img = row.findViewById<ImageView>(R.id.img)
        val ColorName = colorsList[position]
        color.text = ColorName
        val res = context.resources
        var drawableName = ""
        if (ColorName.contains("Рус")) {
            drawableName = "rus_flag"
        }
        if (ColorName.contains("Eng")){
            drawableName = "eng_flag"
        }
        val resId = res.getIdentifier(drawableName, "drawable", context.packageName)
        val drawable = res.getDrawable(resId)
        color_img.setImageDrawable(drawable)
        return row
    }
}