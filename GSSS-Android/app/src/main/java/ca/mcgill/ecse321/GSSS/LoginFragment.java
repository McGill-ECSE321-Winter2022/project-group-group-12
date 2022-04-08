package ca.mcgill.ecse321.GSSS;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

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

    /**
     * Method to send a POST request and login to the system
     *
     * @author Wassim Jabbour
     */
    public void login() {

        // Create a map with the params to pass
        Map<String, String> params = new HashMap<>();
        params.put("email", emailEditText.getText().toString());
        params.put("password", passwordEditText.getText().toString());

        // The post request
        HttpUtils.post("account/login", new RequestParams(params), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                // Set the success message
                setMessage("Logged in successfully!", false);

                // Extract the permission returned from the backend
                String permission;
                try {
                    permission = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    setMessage(e.getMessage(), true);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                // Display the failure message
                try {
                    setMessage(new String(responseBody, "UTF-8"), true);
                } catch (UnsupportedEncodingException e) {
                    setMessage(e.getMessage(), true);
                }
            }
        });
    }

    private void setMessage(String message, boolean error) {

        if (message == null) {
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
                errorTextView.setVisibility(View.INVISIBLE);
            }
        }, 5000);
    }
}