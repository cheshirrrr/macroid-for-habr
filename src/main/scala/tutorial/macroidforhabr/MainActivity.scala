package tutorial.macroidforhabr

import android.app.Activity
import android.os.Bundle
import android.widget.{Button, TextView, LinearLayout}
import macroid.{Ui, Contexts}
import macroid.FullDsl._

class MainActivity extends Activity with Contexts[Activity]{

  var textView = slot[TextView]

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    val view = l[LinearLayout](
      w[TextView] <~ text("Просто надпись") <~ wire(textView),
      w[Button] <~ text("Нажми меня") <~ On.click(changeText)
    ) <~ vertical

    setContentView(getUi(view))
  }

  def changeText : Ui[Any] = {
    textView <~ text("Другая надпись")
  }
}
