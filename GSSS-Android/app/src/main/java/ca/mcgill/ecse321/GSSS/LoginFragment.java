package ca.mcgill.ecse321.GSSS;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // Instance variables
    private String errorText;

    private View rootView;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView errorTextView;

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
        rootView =  inflater.inflate(R.layout.fragment_login, container, false);

        // Bind with UI
        emailEditText = rootView.findViewById(R.id.edit_email);
        passwordEditText = rootView.findViewById(R.id.edit_password);
        loginButton = rootView.findViewById(R.id.button_login);
        errorTextView = rootView.findViewById(R.id.text_error);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        return rootView;
    }

    public void login() {

        // Create a map with the params to pass
        Map<String, String> params = new HashMap<>();
        params.put("email", emailEditText.getText().toString());
        params.put("password", passwordEditText.getText().toString());

        HttpUtils.post("account/login", new RequestParams(params), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                setMessage("Logged in succesfully!", false);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                String error;
                try {
                    error = errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error = e.getMessage();
                }
                setMessage(error, true);
            }
        });
    }

    private void setMessage(String message, boolean error){
        if(message == null){
            errorTextView.setVisibility(View.GONE);
            return;
        }
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
        errorTextView.setTextColor(error ? Color.RED : Color.GREEN);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                errorTextView.setVisibility(View.GONE);
            }
        }, 5000);
    }
}