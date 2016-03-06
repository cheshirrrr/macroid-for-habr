package tutorial.macroidforhabr

import android.app.Fragment
import android.os.Bundle
import android.view.{View, ViewGroup, LayoutInflater}
import android.widget._
import macroid.viewable.SlottedListable
import macroid._
import macroid.FullDsl._
import macroid.contrib.LpTweaks._
import macroid.contrib.TextTweaks._
import macroid.contrib.ImageTweaks._

/**
  * Created by cheshirrrr on 19.01.2016.
  */
class SlottedListableFragment extends Fragment with Contexts[Fragment] with Tweaks with Contacts with IdGeneration {

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = getUi {
    l[LinearLayout](
      w[ListView] <~ ContactListable.listAdapterTweak(fullContactList) <~ matchWidth
    )
  }
}


  object ContactListable extends SlottedListable[Contact] with Contacts with Tweaks with IdGeneration {

    class Slots {
      var name = slot[TextView]
      var photo = slot[ImageView]
    }

    override def fillSlots(slots: Slots, contact: Contact)(implicit ctx: ActivityContext, appCtx: AppContext) = {
      (slots.name <~ text(contact.name)) ~
        (slots.photo <~ roundedDrawable(contact.photo))
    }

    override def makeSlots(viewType: Int)(implicit ctx: ActivityContext, appCtx: AppContext): (Ui[View], ContactListable.Slots) = {

      val slots = new Slots
      val view: Ui[View] = l[LinearLayout](
        w[ImageView] <~ wire(slots.photo)
          <~ adjustBounds
          <~ fitImage
          <~ lp[LinearLayout](150, 150)
          <~ padding(left = 32)
          <~ id(Id.contactImage),
        w[TextView] <~ wire(slots.name) <~ matchParent <~ gravityCenter <~ large
      ) <~ horizontal


      (view, slots)
    }

  }


