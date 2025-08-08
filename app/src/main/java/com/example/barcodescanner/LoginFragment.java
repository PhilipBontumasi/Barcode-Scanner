package com.example.barcodescanner;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    Button btnLogin;
    TextView txtCreate;
    EditText edtEmail, edtPassword;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    LoginFragmentListener mListener;
    private final String TAG = "LoginFragment";


    interface LoginFragmentListener {
        void replaceWithUserFragment();
        void replaceWithCreatorFragment();
        void replaceWithRegisterFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (LoginFragmentListener) context;
        Log.d(TAG, "onAttach: ");
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = view.findViewById(R.id.btnLogin);
        txtCreate = view.findViewById(R.id.txtCreate);
        edtEmail = view.findViewById(R.id.edtEmailAddress);
        edtPassword = view.findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //If there is missing input, show a Toast indicating missing input.
                if (validate()) {
                    String email = edtEmail.getText().toString();
                    String passWord = edtPassword.getText().toString();
                    Log.d(TAG, Thread.currentThread().getId() + "");
                    mAuth = FirebaseAuth.getInstance();
                    signIn(email,passWord);

                }

            }
        });
        txtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.replaceWithRegisterFragment();
            }
        });

        return view;
    }
    /**
     * This method is used to check if an input is valid, if it is not it will send a Toast
     * about what is causing the error
     *
     * @return Returns a Boolean of whether the values for account are valid or not
     */
    public Boolean validate() {
        //Declares the strings
        String email = edtEmail.getText().toString();
        String passWord = edtPassword.getText().toString();

        //Checks email
        if (email.replaceAll("\\s", "").isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msgEmailErrorEmpty), Toast.LENGTH_SHORT).show();
            return false;
        }

        //Checks password
        if (passWord.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msgPasswordErrorEmpty), Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    public void signIn (String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            db = FirebaseFirestore.getInstance();
                            db.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            if (document.getString("userType").equals("User")){
                                                mListener.replaceWithUserFragment();
                                            } else if (document.getString("userType").equals("Creator")){
                                                mListener.replaceWithCreatorFragment();
                                            }
                                        } else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(),task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        Log.d(TAG, Thread.currentThread().getId() + "");
                    }
                });

    }
}