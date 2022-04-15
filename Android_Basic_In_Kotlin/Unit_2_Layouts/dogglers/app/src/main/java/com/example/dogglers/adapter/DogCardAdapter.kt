/*
* Copyright (C) 2021 The Android Open Source Project.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.example.dogglers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogglers.R
import com.example.dogglers.const.Layout
import com.example.dogglers.data.DataSource
import org.w3c.dom.Text

/**
 * Adapter to inflate the appropriate list item layout and populate the view with information
 * from the appropriate data source
 */
class DogCardAdapter(
    private val context: Context?,
    private val layout: Int
): RecyclerView.Adapter<DogCardAdapter.DogCardViewHolder>() {

    private val dogList = DataSource.dogs

    /**
     * Initialize view elements
     */
    class DogCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {

        val ivDogImage: ImageView = view!!.findViewById(R.id.iv_dog_image)
        val tvDogName: TextView = view!!.findViewById(R.id.tv_dog_name)
        val tvDogAge: TextView = view!!.findViewById(R.id.tv_dog_age)
        val tvDogHobbies: TextView = view !!.findViewById(R.id.tv_dog_hobbies)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogCardViewHolder {
        var layoutID = R.layout.vertical_horizontal_list_item

        if (layout == Layout.GRID)
            layoutID = R.layout.grid_list_item

        val adapterLayout = LayoutInflater.from(parent.context).inflate(layoutID, parent, false)

        return DogCardViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = dogList.size

    override fun onBindViewHolder(holder: DogCardViewHolder, position: Int) {

        val resources = context?.resources

        var dog = dogList[position]
        holder.ivDogImage.setImageResource(dog.imageResourceId)
        holder.tvDogName.text = dog.name
        holder.tvDogAge.text = resources?.getString(R.string.dog_age, dog.age)
        holder.tvDogHobbies.text = resources?.getString(R.string.dog_hobbies, dog.hobbies)



    }
}
