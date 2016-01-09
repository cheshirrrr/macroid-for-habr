package tutorial.macroidforhabr

import android.app.Activity
import android.os.Bundle
import macroid.Contexts

/**
  * Created by cheshirrrr on 09.01.2016.
  */
class MainActivity extends Activity with Contexts[Activity]{

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
  }

}
