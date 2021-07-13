package com.example.iikoapi.entities

import com.example.iikoapi.entities.menu.Modifier

sealed class ExpandableEntity<T>: SelectableEntity() {
    var state: ExpandableState = ExpandableState.Collapsed

    abstract val owner: RelationShip

    open val children: List<T>? = null
}

sealed class RelationShip {
    object ParentItem : RelationShip()
    object ChildItem : RelationShip()
}

sealed class ExpandableState {
    object Expanded : ExpandableState()
    object Collapsed : ExpandableState()
}

////////////////////////////////////////////////////////////////////////////////////////////////////


// Remove
data class ChildRemove(
    override val owner: RelationShip = RelationShip.ChildItem,
    val item: Modifier
) : ExpandableEntity<ChildRemove>(){
    override var isSelected: Boolean = false
}

data class ParentRemove(
    override val owner: RelationShip = RelationShip.ParentItem,
    val parent: String, override val children: List<ChildRemove>
) : ExpandableEntity<ChildRemove>(){
    override var isSelected: Boolean = false
}

// District
data class ChildDistrict(
    override val owner: RelationShip = RelationShip.ChildItem,
    val item: MerchItem
) : ExpandableEntity<ChildDistrict>(){
    override var isSelected: Boolean = false
}

data class ParentDistrict(
    override val owner: RelationShip = RelationShip.ParentItem,
    val parent: String, override val children: List<ChildDistrict>
) : ExpandableEntity<ChildDistrict>(){
    override var isSelected: Boolean = false
}