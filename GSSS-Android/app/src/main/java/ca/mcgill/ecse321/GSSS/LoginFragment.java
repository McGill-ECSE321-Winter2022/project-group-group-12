package ca.mcgill.ecse321.GSSS;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // Instance variables
    private String errorText;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void login(View v) {

        // Reset the error
        errorText = "";

        // Get the textfields from the view
        final TextView loginEmail = (TextView) findViewById(R.id.loginEmail);
        final TextView loginPassword = (TextView) findViewById(R.id.loginPassword);

        // Create a map with the params to pass
        Map<String, String> params = new HashMap<>();
        params.put("email", loginEmail.getText().toString());
        params.put("password", loginPassword.getText().toString());

        HttpUtils.post("account/login", new RequestParams(params), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                errorText = "Logged in successfully";
                refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    errorText += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    errorText += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(errorText);

        if (errorText == null || errorText.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
}