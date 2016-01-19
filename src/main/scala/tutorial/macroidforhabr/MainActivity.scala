package tutorial.macroidforhabr

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.{ListView, Button, TextView, LinearLayout}
import macroid.viewable.Listable
import macroid._
import macroid.FullDsl._

import scala.concurrent.ExecutionContext.Implicits.global

class MainActivity extends Activity with Contexts[Activity]{

  var textView = slot[TextView]
  var layout = slot[LinearLayout]

def contactList = Seq("Контакт 1", "Контакт 2", "Контакт 3")

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    val view = l[LinearLayout](
      w[TextView] <~ text("Просто надпись") <~ wire(textView),
      w[Button] <~ text("Нажми меня") <~ On.click(changeText),
      l[LinearLayout](
        w[TextView] <~ text("Мигающий лэйаут"),
        w[ListView] <~ basicListable.listAdapterTweak(contactList)
      ) <~ wire(layout) <~ vertical
    ) <~ vertical

    setContentView(getUi(view))
  }

  def changeText : Ui[Any] = {
    (textView <~ text("Помигаем?")) ~ (layout <~~ flashElement) ~~ (textView <~ text("Ну и хватит"))
  }

  def flashElement : Snail[View] = {
      fadeOut(500) ++ fadeIn(500) ++ fadeOut(500) ++ fadeIn(500)
  }

  def basicListable(implicit appCtx: AppContext): Listable[String, TextView] = {
    Listable[String].tw { w[TextView] } { text(_) }
  }
}
