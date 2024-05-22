package com.hoant.taipeitour.view.languages

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoant.taipeitour.R
import com.hoant.taipeitour.base.BaseAdapter
import com.hoant.taipeitour.databinding.FragmentSelectionLanguagesBinding
import com.hoant.taipeitour.viewmodel.attraction.AttractionViewModel

class SelectionLanguageDialog: DialogFragment(), BaseAdapter.OnItemClick<String>  {
    private lateinit var languagesAdapter: LanguagesAdapter
    private lateinit var binding: FragmentSelectionLanguagesBinding
    private val viewModel: AttractionViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSelectionLanguagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languagesAdapter = LanguagesAdapter(this)
        binding.rcvLanguages.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = languagesAdapter
        }

        val array: List<String> = resources.getStringArray(R.array.arrLanguages).toList()
        languagesAdapter.differ.submitList(array)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return dialog
    }


    override fun onItemCLicked(item: String) {
        viewModel.setLanguageSelected(item)
        dismiss()
    }
}