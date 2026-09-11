package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuFragment : Fragment() {

    // Danh sách món ăn mẫu — sau này có thể thay bằng dữ liệu lấy từ API backend
    private val allFoods = listOf(
        FoodItem(
            id = 1,
            name = "Burger bò phô mai",
            description = "Burger bò kèm phô mai",
            price = 59000,
            emoji = "🍔",
            category = "Burger"
        ),
        FoodItem(
            id = 2,
            name = "Pizza Hải Sản",
            description = "Pizza hải sản phô mai",
            price = 129000,
            emoji = "🍕",
            category = "Pizza"
        ),
        FoodItem(
            id = 3,
            name = "Gà rán giòn",
            description = "Gà rán giòn thơm ngon",
            price = 79000,
            emoji = "🍗",
            category = "Gà"
        ),
        FoodItem(
            id = 4,
            name = "Coca Cola",
            description = "Nước ngọt Coca Cola",
            price = 20000,
            emoji = "🥤",
            category = "Đồ uống"
        )
    )

    private lateinit var adapter: FoodAdapter
    private lateinit var txtCategoryTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_menu,
            container,
            false
        )

        txtCategoryTitle = view.findViewById(R.id.txtCategoryTitle)

        val recyclerFood = view.findViewById<RecyclerView>(R.id.recyclerFood)
        recyclerFood.layoutManager = LinearLayoutManager(requireContext())

        adapter = FoodAdapter(allFoods) { food ->
            onAddToCart(food)
        }
        recyclerFood.adapter = adapter

        // Lấy category được truyền từ HomeFragment (nếu có)
        val category = arguments?.getString("category")
        if (category != null) {
            filterByCategory(category)
        }

        // =========================
        // CATEGORY BUTTONS
        // =========================

        view.findViewById<View>(R.id.btnAll).setOnClickListener {
            filterByCategory("Tất cả")
        }

        view.findViewById<View>(R.id.btnBurgerCategory).setOnClickListener {
            filterByCategory("Burger")
        }

        view.findViewById<View>(R.id.btnPizzaCategory).setOnClickListener {
            filterByCategory("Pizza")
        }

        view.findViewById<View>(R.id.btnChickenCategory).setOnClickListener {
            filterByCategory("Gà")
        }

        view.findViewById<View>(R.id.btnDrinkCategory).setOnClickListener {
            filterByCategory("Đồ uống")
        }

        return view
    }

    // =========================
    // FILTER CATEGORY
    // =========================

    private fun filterByCategory(category: String) {
        if (category == "Tất cả") {
            txtCategoryTitle.text = "Tất cả món ăn"
            adapter.updateData(allFoods)
        } else {
            txtCategoryTitle.text = category
            adapter.updateData(allFoods.filter { it.category == category })
        }
    }

    // =========================
    // ADD TO CART
    // =========================

    private fun onAddToCart(food: FoodItem) {
        CartManager.addItem(food)

        Toast.makeText(
            requireContext(),
            "Đã thêm ${food.name} vào giỏ hàng",
            Toast.LENGTH_SHORT
        ).show()

        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                CartFragment()
            )
            .addToBackStack(null)
            .commit()
    }
}
