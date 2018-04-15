package com.gildedrose

class GildedRose(val items: Array[Item]) {

  def updateQuality() {
    val updated = items.toList.map { item =>
      ExtendedItem(item).updateQuality.toItem
    }.zipWithIndex

    // don't like this as while we know that updated has the same number
    // of elements as items, it's still a risk (e.g. when we need to change the code again)
    updated.foreach { case (item, index) =>
      items(index) = item
    }
  }
}