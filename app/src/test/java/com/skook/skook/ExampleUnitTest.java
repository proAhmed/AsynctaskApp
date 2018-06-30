package com.skook.skook;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
//    @Test
//    public void testPostFormWithFieldMap() throws IOException {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://httpbin.org/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        NetworkInterfaces.GetOffers service = retrofit.create(NetworkInterfaces.GetOffers.class);
//
//        Map<String, String> fieldsMap = new HashMap<>();
//        fieldsMap.put(NetworkConstant.SECURE_DATA_KEY,NetworkConstant.SECURE_DATA);
//        fieldsMap.put(NetworkConstant.OFFSET_KEY,"1");
//        fieldsMap.put(NetworkConstant.LIMIT_KEY,"10");
//
//        Call<String> call = service.getOffers(fieldsMap);
//        call.enqueue(new Callback <String>() {
//            @Override
//            public void onResponse(Call< String> call, Response <String> response) {
//
//                Log.d("ppp00",response.raw().headers().toString());
//
//
//            }
//
//            @Override
//            public void onFailure(Call <String> call, Throwable t) {
//                Log.d("ppp11",t.getMessage());
//                //onLoadingData.onFaliure(t.getMessage());
//
//            }
//        });
/////        Response<String> response = call.execute();
//     //   assertTrue(response.isSuccessful());
//
//    }
}