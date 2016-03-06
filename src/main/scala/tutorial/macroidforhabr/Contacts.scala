package tutorial.macroidforhabr

case class Contact(name:String, phone: String, photo: Int)

trait Contacts {
  def contactList = Seq("Контакт 1", "Контакт 2", "Контакт 3")

  def fullContactList = Seq(
    Contact("Mr Jaguar", "5555555",R.drawable.jaguar ),
    Contact("Mr Labrador", "6666666",R.drawable.labrador ),
    Contact("Mr Wolf", "7777777",R.drawable.wolf )
  )
}
