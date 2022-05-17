package com.example.studdybuddy.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.studdybuddy.R;
import com.example.studdybuddy.activities.LogInActivity;
import com.example.studdybuddy.databinding.FragmentNotificationsBinding;
import com.example.studdybuddy.utilities.Constants;
import com.example.studdybuddy.utilities.SavePreferences;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Objects;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private SavePreferences savePreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //LOG OUT BUTTON DO NOT MODIFY --- START --- ///
        setHasOptionsMenu(true);
        savePreferences = new SavePreferences(getContext());
        // --- END --- //
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    //-----THE FOLLOWING FUNCTIONS ARE NEEDED IN ORDER TO LOG OUT ---- START ---- //
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_log_out, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        signOut();
        return true;
    }

    private void signOut(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(
                savePreferences.getString(Constants.KEY_USER_ID)
        );
        HashMap<String, Object> update = new HashMap<>();
        update.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(update).addOnSuccessListener(unused -> {
            savePreferences.clear();
            Intent intent = new Intent(getContext(), LogInActivity.class);
            startActivity(intent);
        }).addOnFailureListener(e -> { Log.e("Token", "Error while updating token: " + e.getMessage());
        });
    }

    // --------- END ---------- //
}