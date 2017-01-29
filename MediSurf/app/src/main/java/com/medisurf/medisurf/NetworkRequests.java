package com.medisurf.medisurf;

import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kiran Konduru on 11/4/2016.
 */
public class NetworkRequests {

    public static void Home() {
        HashMap<String, String> params = new HashMap<String, String>();
        System.out.println(URLGenerator.getUrl(URLGenerator.index));
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.index), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Log.e("Status is True", response.toString());

                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                Log.e("Status is True", response.toString());
                            } else {
                                Log.e("Status is True", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error" + error);

            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void genericsalt(String medicine_name) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("med_name", medicine_name);
        System.out.println(URLGenerator.getUrl(URLGenerator.genericsalt));
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.genericsalt), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Log.e("Status is True", response.toString());
                                getSalt.getInstance().processFinish(response.toString());
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                Log.e("Status is True", response.toString());
                            } else {
                                Log.e("Status is True", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error" + error);

            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void showbrands(String medicine_name) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("med_name", medicine_name);
        System.out.println(URLGenerator.getUrl(URLGenerator.showbrands));
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.showbrands), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Log.e("Status is True", response.toString());
                                GetBrands.getInstance().processFinish(response.toString());
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                Log.e("Status is True", response.toString());
                            } else {
                                Log.e("Status is True", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error" + error);

            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 20,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void getbrands(String medicine_name) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("med_name", medicine_name);
        System.out.println(URLGenerator.getUrl(URLGenerator.getbrands));
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.getbrands), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Log.e("Status is True", response.toString());
                                getAlternatives.getInstance().onSuccess(response);
                            } else {
                                Log.e("Status is True", response.toString());
                                getAlternatives.getInstance().onFailure(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error" + error);

            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void optimisebill(HashMap params) {
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("mn",mn);
//        params.put("ut",ut);
//        params.put("mgml", mgml);
//        params.put("total_med", total_med);
        System.out.println(URLGenerator.getUrl(URLGenerator.optimisebill));
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.optimisebill), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Log.e("Status is True", response.toString());
                                OptimizeBill.getInstance().processFinish(response.getString("results"));

                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                Log.e("Status is True", response.toString());
                            } else {
                                Log.e("Status is True", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error" + error);

            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 24,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void savestat(HashMap params) {
        System.out.println(URLGenerator.getUrl(URLGenerator.savestat));
        System.out.println("param"  +params);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.savestat), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Log.e("Status is True", response.toString());
                                Display_Bill.getmInstance().onDataReturn();
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                Log.e("Status is True", response.toString());
                            } else {
                                Log.e("Status is True", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error" + error);

            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

}