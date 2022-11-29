package com.mbkm.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView result, input;
    MaterialButton btnC, btnAC, btnDel;
    MaterialButton btnEquals, btnPercent, btnDiv, btnMultiply, btnPlus, btnMinus;
    MaterialButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnComa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        input = findViewById(R.id.input);

        value(btnC,R.id.C);
        value(btnAC,R.id.AC);
        value(btnDel,R.id.delete);
        value(btnEquals,R.id.equals);
        value(btnPercent,R.id.percent);
        value(btnDiv,R.id.divide);
        value(btnMultiply,R.id.multiplication);
        value(btnPlus,R.id.plus);
        value(btnMinus,R.id.minus);
        value(btn0,R.id.zero);
        value(btn1,R.id.one);
        value(btn2,R.id.two);
        value(btn3,R.id.three);
        value(btn4,R.id.four);
        value(btn5,R.id.five);
        value(btn6,R.id.six);
        value(btn7,R.id.seven);
        value(btn8,R.id.eight);
        value(btn9,R.id.nine);
        value(btnComa,R.id.coma);
    }

    void value(MaterialButton btn, int value){
        btn = findViewById(value);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String btnText = button.getText().toString();
        String calculate = input.getText().toString();

        if(btnText.equals("AC")){
            input.setText("");
            result.setText("0");
            return;
        }

        if(btnText.equals("C")){
            input.setText("");
            return;
        }



        if(btnText.equals("=")){

            input.setText(result.getText());
            return;
        }

        if(btnText.equals("del")){
            try {
                calculate = calculate.substring(0,calculate.length()-1);
                if(calculate.length() == 0){
                    input.setText("");
                    result.setText("0");
                    return;
                }
            }catch (Exception e){
                return;
            }
        }
        else{
            calculate = calculate+btnText;
        }
        calculate = calculate.replaceAll("%", "/100");
        result.setText(getResult(calculate));
        input.setText(calculate);
        String finalResult = getResult(calculate);

        if(!finalResult.equals("Error")){
            result.setText(finalResult);
        }
    }

    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel((-1));
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data, "Javascript", 1, null).toString();

            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0"," ");
            }
            return  finalResult;
        }catch (Exception e){
            return "";
        }
    }
}