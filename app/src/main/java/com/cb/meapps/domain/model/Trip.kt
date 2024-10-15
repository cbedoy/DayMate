package com.cb.meapps.domain.model

data class Trip(
    val name: String,
    val members: List<Member>,
    val expenses: List<Expense>
)


data class Expense(
    val name: String,
    val amount: Float
)
data class Member(
    val nickname: String
)

fun getFakeTrips(): List<Trip> {
    return listOf(
        Trip(
            name = "Music Festival Weekend Getaway",
            members = listOf(
                Member("me"),
                Member("daniela"),
                Member("carlos"),
                Member("andrea")
            ),
            expenses = listOf(
                Expense("airbnb accommodation", 850.00f),
                Expense("meals at the festival", 150.00f),
                Expense("drinks and snacks", 250.00f),
                Expense("festival tickets", 300.00f)
            )
        ),
        Trip(
            name = "Annual Friends Beach Retreat",
            members = listOf(
                Member("me"),
                Member("daniela"),
                Member("juan"),
                Member("mariana"),
                Member("luis")
            ),
            expenses = listOf(
                Expense("beach house rental", 1200.00f),
                Expense("groceries and cooking supplies", 300.00f),
                Expense("surfboard rentals", 180.00f),
                Expense("gas and parking fees", 75.00f)
            )
        ),
        Trip(
            name = "Road Trip to National Park Adventure",
            members = listOf(
                Member("me"),
                Member("daniela"),
                Member("victor")
            ),
            expenses = listOf(
                Expense("campground fees", 50.00f),
                Expense("gasoline", 100.00f),
                Expense("hiking gear rental", 60.00f),
                Expense("park entrance fees", 40.00f)
            )
        ),
        Trip(
            name = "Winter Ski Resort Escape",
            members = listOf(
                Member("me"),
                Member("daniela"),
                Member("alejandra"),
                Member("roberto")
            ),
            expenses = listOf(
                Expense("ski resort cabin", 900.00f),
                Expense("ski and snowboard rentals", 200.00f),
                Expense("ski lift passes", 320.00f),
                Expense("hot chocolate and snacks", 50.00f)
            )
        )
    )
}