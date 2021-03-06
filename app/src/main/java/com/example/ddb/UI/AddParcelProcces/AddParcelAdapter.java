package com.example.ddb.UI.AddParcelProcces;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.LinkedList;

public class AddParcelAdapter extends FragmentPagerAdapter implements GetDataInterface {
    private static LinkedList<DataGetterFragment> fragments = new LinkedList<>();

    static {
        fragments.add(new AddParcelFragment1());
        fragments.add(new AddParcelFragment2());
        fragments.add(new AddParcelFragment3());
    }

    public AddParcelAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public HashMap<String, Object> getData() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> hashMap1;
        for (DataGetterFragment fragment : fragments) {
            try {
                hashMap1 = fragment.getData();
            } catch (Exception e) {
                throw new Exception(e);
            }
            for (String key : hashMap1.keySet()) {
                hashMap.put(key, hashMap1.get(key));
            }
        }
        return hashMap;
    }

    @Override
    public boolean allFieldsFull() {
        for (GetDataInterface fragment : fragments) {
            if (!fragment.allFieldsFull())
                return false;
        }
        return true;
    }
}
