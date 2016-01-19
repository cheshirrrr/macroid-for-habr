package tutorial.macroidforhabr

import android.app.Activity
import android.nfc.Tag
import android.os.Bundle
import android.widget.{ListView, Button, TextView, LinearLayout}
import macroid._
import macroid.FullDsl._

import scala.concurrent.ExecutionContext.Implicits.global

class MainActivity extends Activity with Contexts[Activity] with Contacts with Tweaks with IdGeneration{

  var textView = slot[TextView]
  var layout = slot[LinearLayout]

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    val view = l[LinearLayout](
      w[TextView] <~ text("Просто надпись") <~ wire(textView),
      w[Button] <~ text("Нажми меня") <~ On.click(changeText),
      l[LinearLayout](
        w[TextView] <~ text("Мигающий лэйаут"),
        f[ListableFragment].framed(Id.contactList, Tag.contactList)
      ) <~ wire(layout) <~ vertical
    ) <~ vertical

    setContentView(getUi(view))
  }

  def changeText : Ui[Any] = {
    (textView <~ text("Помигаем?")) ~ (layout <~~ flashElement) ~~ (textView <~ text("Ну и хватит"))
  }
}
