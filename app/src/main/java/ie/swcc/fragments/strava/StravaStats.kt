package ie.swcc.fragments.strava


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager

import ie.swcc.R
import ie.swcc.adapters.StravaAdapter2
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.StravaStatsModel
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_strava_report2.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StravaStats : Fragment(), Callback<List<StravaStatsModel>>, AnkoLogger {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_strava_report2, container, false)
        activity?.title = getString(R.string.action_strava)
        loader = createLoader(activity!!)

        root.recyclerView2.setLayoutManager(LinearLayoutManager(activity))
        root.recyclerView2.adapter = StravaAdapter2(app.stravaStore.findAllStats())

        return root
    }



    override fun onResponse(call: Call<List<StravaStatsModel>>,
                            response: Response<List<StravaStatsModel>>
    ) {
        serviceAvailableMessage(activity!!)
        info("Retrofit JSON = $response.raw()")
        app.stravaStore.activities = response.body() as ArrayList<StravaStatsModel>
        //updateUI()
        hideLoader(loader)
    }

    override fun onFailure(call: Call<List<StravaStatsModel>>, t: Throwable) {
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(activity!!)
        hideLoader(loader)
    }


    override fun onResume() {
        super.onResume()
        getAllActivities()
    }
    fun getAllActivities() {
        showLoader(loader, "Downloading Strava List")
        var callGetAll = app.stravaService.getall2()
        callGetAll.enqueue(this)

    }




    companion object {
        @JvmStatic
        fun newInstance() =
            StravaStats().apply {
                arguments = Bundle().apply { }
            }
    }




}


