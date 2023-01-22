package it.giaquinto.stargazersviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import it.giaquinto.stargazersviewer.model.InformationMessage
import it.giaquinto.stargazersviewer.model.InformationType
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private var _info: MutableLiveData<InformationMessage> = MutableLiveData(InformationMessage("Nothing to show", InformationType.INFO))
    val info: LiveData<InformationMessage> = _info

    private var _list: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val list: LiveData<List<String>> = _list

    fun search(data: String): Boolean {
        if (data.length >= 3) {
            _info.postValue(
                InformationMessage(
                    "Searching...",
                    InformationType.INFO
                )
            )
            _list.postValue(
                listOf("CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO","CIAO",)
            )
        } else {
            _info.postValue(
                InformationMessage(
                    "Type at least three characters to search",
                    InformationType.ERROR
                )
            )
        }

        return true
    }
}