package com.cb.meapps.domain.fake

import com.cb.meapps.domain.model.Card
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