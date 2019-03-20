package com.contraslash.android.mini_llo.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.contraslash.android.mini_llo.activities.BoardDetail;
import com.contraslash.android.mini_llo.config.Conf;
import com.contraslash.android.mini_llo.databinding.ElementBoardBinding;
import com.contraslash.android.mini_llo.models.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardArrayAdapter extends ArrayAdapter<Board> {
    private Context context;
    private int resource;
    private ArrayList<Board> elements;

    public BoardArrayAdapter(@NonNull Context context, int resource, @NonNull List<Board> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.elements = (ArrayList<Board>)objects;
    }

    @Override
    public int getCount() {
        return this.elements.size();
    }

    @Nullable
    @Override
    public Board getItem(int position) {
        return this.elements.get(position);
    }

    @Override
    public void clear() {
        this.elements.clear();
    }

    @Override
    public void add(@Nullable Board object) {
        this.elements.add(object);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ElementBoardBinding binding = DataBindingUtil.getBinding(convertView);
        if(convertView == null) {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    this.resource,
                    parent,
                    false
            );
            convertView = binding.getRoot();
        }
        final Board actualBoard =  elements.get(position);

        binding.elementBoardName.setText(actualBoard.getName());

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToBatchDetail = new Intent(context, BoardDetail.class);
                goToBatchDetail.putExtra(Conf.BOARD_ID, elements.get(position).getId());
                goToBatchDetail.putExtra(Conf.BOARD_NAME, elements.get(position).getName());
                context.startActivity(goToBatchDetail);
            }
        });

        return convertView;
    }

}
