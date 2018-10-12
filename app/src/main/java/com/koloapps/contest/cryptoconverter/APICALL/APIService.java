package com.koloapps.contest.cryptoconverter.APICALL;



import com.koloapps.contest.cryptoconverter.Model.CryptoCrcy;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Johnjahnless on 11/1/2017.
 */

public interface APIService {
    //API call to get json object & append the URL
    @GET("/data/pricemulti?fsyms=DOGE,PPC,EOS,ADA,NEO,XRP,ZEC,BCH,LTC,XMR,DASH,REP,BTC,ETH&tsyms=USD")
    Call<CryptoCrcy> getBTC();
}
