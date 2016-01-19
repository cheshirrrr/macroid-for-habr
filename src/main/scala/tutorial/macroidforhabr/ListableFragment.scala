package tutorial.macroidforhabr

import android.app.Fragment
import android.os.Bundle
import android.view.{View, ViewGroup, LayoutInflater}
import android.widget.{TextView, ListView, LinearLayout}
import macroid.viewable.Listable
import macroid.{AppContext, Contexts}
import macroid.FullDsl._
import macroid.contrib.LpTweaks._

class ListableFragment extends Fragment with Contexts[Fragment] with Tweaks with Contacts{

  def basicListable(implicit appCtx: AppContext): Listable[String, TextView] = {
    Listable[String].tw { w[TextView] } { text(_) }
  }
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = getUi {
    l[LinearLayout](
      w[ListView] <~ basicListable.listAdapterTweak(contactList) <~ matchWidth
    )
  }
}
