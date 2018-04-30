package shsato.tk.watchplant.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel() {
	var title: MutableLiveData<String>? = null
		get() {
			if (field == null) {
				field = MutableLiveData()
			}
			return field
		}
}