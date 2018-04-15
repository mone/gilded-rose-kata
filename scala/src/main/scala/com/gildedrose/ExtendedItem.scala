package com.gildedrose

// TODO N6 use tagged types instead of Int:s
sealed abstract class ExtendedItem(name: String, sellIn: Int, quality: Int) {
  def updateQuality: ExtendedItem

  def toItem: Item = new Item(name, sellIn, quality)
}

object ExtendedItem {

  /**
    * Increases a quality by the specified amount (max quality is 50)
    */
  def increaseQuality(quality: Int, incBy: Int = 1): Int =
    if (quality + incBy > 50) 50 else quality + incBy

  /**
    * Decreases a quality by the specified amount (min quality is 0)
    */
  def decreaseQuality(quality: Int, decBy: Int = 1): Int =
    if (quality - decBy < 0) 0 else quality - decBy

  def decreaseSellIn(sellIn: Int): Int = sellIn - 1

  // TODO N5 check on exact name is risky, original item should include a category
  // (if not possible discuss the matching rules to verify they are correct)
  def apply(item: Item): ExtendedItem = item.name match {
    case "Aged Brie" =>
      GainQualityWithAgeItem(item.name, item.sellIn, item.quality)

    case "Backstage passes to a TAFKAL80ETC concert" =>
      BackstagePass(item.name, item.sellIn, item.quality)

    case "Sulfuras, Hand of Ragnaros" =>
      LegendaryItem(item.name, item.sellIn)

    case _ =>
      GenericItem(item.name, item.sellIn, item.quality)
  }

  /**
    * Generic item loses quality as it ages, once expired loses quality twice as fast
    */
  case class GenericItem(
    name: String, sellIn: Int, quality: Int
  ) extends ExtendedItem(name, sellIn, quality) {

    def updateQuality: ExtendedItem = {
      val decBy = if (sellIn <= 0) 2 else 1

      GenericItem(name, decreaseSellIn(sellIn), decreaseQuality(quality, decBy))
    }

  }

  /**
    * Item that gains quality as it ages
    */
  case class GainQualityWithAgeItem(
    name: String, sellIn: Int, quality: Int
  ) extends ExtendedItem(name, sellIn, quality) {

    def updateQuality: ExtendedItem = {
      GainQualityWithAgeItem(name, decreaseSellIn(sellIn), increaseQuality(quality))
    }

  }

  /**
    * Backstage pass for a concert, as the concert gets closer its quality increases,
    * after the concert is worth nothing
    */
  case class BackstagePass(
    name: String, sellIn: Int, quality: Int
  ) extends ExtendedItem(name, sellIn, quality) {

    def updateQuality: ExtendedItem = {
      val newQuality = sellIn match {
        case v if v <= 0 => 0
        case v if v <= 5 => increaseQuality(quality,3)
        case v if v <= 10 => increaseQuality(quality,2)
        case _ => increaseQuality(quality)
      }
      BackstagePass(name, decreaseSellIn(sellIn), newQuality)
    }

  }

  val LegendaryQuality = 80

  /**
    * Legendary item is legendary
    */
  case class LegendaryItem(
    name: String, sellIn: Int
  ) extends ExtendedItem(name, sellIn, LegendaryQuality) {
    def updateQuality: ExtendedItem = this
  }


}


