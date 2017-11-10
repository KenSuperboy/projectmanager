package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.orhanobut.logger.Logger;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushReqSelectTimeActivity extends BasicActivity implements DatePickerController {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.pickerView)
    DayPickerView pickerView;
    @BindView(R.id.tips)
    TextView tips;
    private int type = 0;
    private SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays;//选择的时间范围
    private SimpleMonthAdapter.CalendarDay f;
    private SimpleMonthAdapter.CalendarDay l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_req_select_time);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("播放时间");
        pickerView.setController(this);
        selectedDays = (SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay>) getIntent().getExtras().get("selectedDays");
        f = selectedDays.getFirst();
        l = selectedDays.getLast();

        pickerView.getSelectedDays().setFirst(f);
        pickerView.getSelectedDays().setLast(l);
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    @Override
    public int getMaxYear() {
        return 2018;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        Logger.e("Day Selected" + day + " / " + month + " / " + year);
        if (type == 0) {
            tips.setText("请选择启播时间");
            type = 1;
        } else {
            tips.setText("请选择结束时间");
            type = 0;
        }
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
        Logger.e(selectedDays.getFirst().getDate().toString() + "```" + selectedDays.getLast().getDate().toString());
        setResult(RESULT_OK, new Intent().putExtra("selectedDays", selectedDays));
        finish();
    }
}
