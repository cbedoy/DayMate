package com.cb.meapps.domain.fake

import com.cb.meapps.data.model.AdditionalPayment
import com.cb.meapps.domain.model.Card
import com.cb.meapps.domain.model.CardPayment
import com.cb.meapps.domain.model.CombinedProjection
import com.cb.meapps.domain.model.Document
import com.cb.meapps.domain.model.FileType
import com.cb.meapps.domain.model.FuelTracker

fun getFakeCards(): List<Card> {
    return listOf(
        Card(id = 1, name = "Invex", cutOffDate = 20, dueDate = 10, debt = 21000f),
        Card(id = 2, name = "Hey Banco", cutOffDate = 25, dueDate = 15, debt = 8000f),
        Card(id = 3, name = "Rappi", cutOffDate = 15, dueDate = 5, debt = 6000f),
        Card(id = 4, name = "Citibanamex", cutOffDate = 14, dueDate = 4, debt = 3600f)
    )
}

fun getFakeTracks(): List<FuelTracker> {
    return listOf(
        FuelTracker(id = 1, totalKM = 560f, totalPrice = 988f, liters = 40f),
        FuelTracker(id = 2, totalKM = 550f, totalPrice = 1000f, liters = 42.5f),
        FuelTracker(id = 3, totalKM = 120f, totalPrice = 200f, liters = 8.1f),
        FuelTracker(id = 4, totalKM = 347f, totalPrice = 1000f, liters = 42.3f),
    )
}

fun getFakeDocs(): List<Document> {
    return listOf(
        Document(
            fileName = "file1.pdf",
            alias = "Document PDF 1",
            filePath = "/storage/emulated/0/Documents/file1.pdf",
            fileType = FileType.PDF
        ),
        Document(
            fileName = "image1.png",
            alias = "Profile Image",
            filePath = "/storage/emulated/0/Pictures/image1.png",
            fileType = FileType.IMAGE
        ),
        Document(
            fileName = "video1.mp4",
            alias = "Vacation Video",
            filePath = "/storage/emulated/0/Movies/video1.mp4",
            fileType = FileType.VIDEO
        ),
        Document(
            fileName = "audio1.mp3",
            alias = "Favorite Song",
            filePath = "/storage/emulated/0/Music/audio1.mp3",
            fileType = FileType.AUDIO
        ),
        Document(
            fileName = "file2.docx",
            alias = "Work Document",
            filePath = "/storage/emulated/0/Documents/file2.docx",
            fileType = FileType.OTHER
        ),
        Document(
            fileName = "presentation.pptx",
            alias = "Project Presentation",
            filePath = "/storage/emulated/0/Documents/presentation.pptx",
            fileType = FileType.OTHER
        ),
        Document(
            fileName = "file3.pdf",
            alias = "User Manual",
            filePath = "/storage/emulated/0/Documents/file3.pdf",
            fileType = FileType.PDF
        ),
        Document(
            fileName = "image2.jpg",
            alias = "Landscape Image",
            filePath = "/storage/emulated/0/Pictures/image2.jpg",
            fileType = FileType.IMAGE
        )
    )
}

fun getFakeCombinedProjections(): List<CombinedProjection> {
    return listOf(
        CombinedProjection(
            date = "15 Jan",
            current = "$1000",
            paymentToday = "$500",
            dailyInterest = "$100",
            accumulatedInterest = "$10",
            totalSavings = "$100",
            isPaymentDay = false,
            cardPayments = listOf(
                CardPayment("CitiBanamex", 300.0f),
                CardPayment("American Express", 500.0f)
            )
        ),
        CombinedProjection(
            date = "16 Jan",
            current = "$1200",
            paymentToday = "$600",
            dailyInterest = "$120",
            accumulatedInterest = "$20",
            totalSavings = "$150",
            isPaymentDay = true,
            cardPayments = listOf(
                CardPayment("HSBC", 400.0f),
                CardPayment("Santander", 250.0f)
            )
        ),
        CombinedProjection(
            date = "17 Jan",
            current = "$1300",
            paymentToday = "$300",
            dailyInterest = "$110",
            accumulatedInterest = "$30",
            totalSavings = "$200",
            isPaymentDay = false,
            cardPayments = listOf(
                CardPayment("BBVA", 100.0f)
            )
        ),
        CombinedProjection(
            date = "18 Jan",
            current = "$1400",
            paymentToday = "$700",
            dailyInterest = "$130",
            accumulatedInterest = "$40",
            totalSavings = "$300",
            isPaymentDay = true,
            cardPayments = listOf(
                CardPayment("Scotiabank", 500.0f),
                CardPayment("Banorte", 200.0f)
            )
        )
    )
}

fun getFakeAdditionalPayments(): List<AdditionalPayment> {
    return listOf(
        AdditionalPayment("Tax return", 32, 1000f),
        AdditionalPayment("Warranty return", 100, 500f),
        AdditionalPayment("Family expense", 200, 800f)
    )
}