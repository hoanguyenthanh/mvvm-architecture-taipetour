package com.hoant.taipeitour

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.hoant.taipeitour.base.BaseActivity
import com.hoant.taipeitour.base.ViewModelProviderFactory
import com.hoant.taipeitour.databinding.ActivityMainBinding
import com.hoant.taipeitour.repository.api.ApiClient
import com.hoant.taipeitour.repository.repo.AttractionRepository
import com.hoant.taipeitour.view.languages.SelectionLanguageDialog
import com.hoant.taipeitour.viewmodel.attraction.AttractionViewModel

class MainActivity: BaseActivity<AttractionViewModel, ActivityMainBinding, AttractionRepository>() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    override var variableId: Int? = null

    override fun createViewDataBinding(): ActivityMainBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun createRepository(): AttractionRepository {
        return AttractionRepository(ApiClient.attractionApi)
    }

    override fun createViewModel(): AttractionViewModel {
        val factory = ViewModelProviderFactory(application, createRepository())
        return ViewModelProvider(viewModelStore, factory).get(AttractionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        setSupportActionBar(viewDataBinding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        initObserves()
    }

    private fun initObserves() {
        viewModel.languageSelected.observe(this, Observer {
            it?.let {
                if (MyApplication.language != it) {
                    setLocaleLocal(it)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_language -> {
                showLanguageDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun configToolbar(title: String) {
        supportActionBar?.title = title
    }


    private fun showLanguageDialog() {
        val dialogFragment = SelectionLanguageDialog()
        dialogFragment.show(supportFragmentManager, "LanguagesDialog")
    }
}