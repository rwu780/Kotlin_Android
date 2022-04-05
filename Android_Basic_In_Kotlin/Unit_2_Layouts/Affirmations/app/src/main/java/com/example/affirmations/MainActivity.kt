package com.example.affirmations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmations.adapter.ItemAdapter
import org.w3c.dom.Text
import javax.sql.DataSource

import com.example.affirmations.data.Datasource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val textView: TextView = findViewById(R.id.textview)
//        textView.text = Datasource.loadAffirmation().size.toString()

        val myDataSet = Datasource.loadAffirmation()

        val recycleView: RecyclerView = findViewById(R.id.recycler_view)
        recycleView.adapter = ItemAdapter(this, myDataSet)
        recycleView.setHasFixedSize(true)

    }
}