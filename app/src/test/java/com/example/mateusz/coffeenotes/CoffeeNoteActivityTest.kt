package com.example.mateusz.coffeenotes

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TestApplication::class)
class CoffeeNoteActivityTest {
    private lateinit var beansTypeDataManager: BeansTypeDataManager

    @Before
    fun setUp() {
        beansTypeDataManager =
                (RuntimeEnvironment.application as TestApplication).getAppComponent().dataManager()
    }

    @Test
    fun initState() {
        val beansType = createDefaultBeansType()
        val id = UUID.randomUUID()
        val coffeeNote = createDefaultCoffeeNote(id, beansType)
        val file = File(
                RuntimeEnvironment.application.applicationContext.filesDir, "test.png")
        file.createNewFile()
        whenever(beansTypeDataManager.getPhotoFile(beansType)).thenReturn(file)
        whenever(beansTypeDataManager.getBeansTypeById(beansType.id)).thenReturn(beansType)
        whenever(beansTypeDataManager.getCoffeeNoteById(id)).thenReturn(coffeeNote)

        val activity =
                Robolectric.buildActivity(
                        CoffeeNoteActivity::class.java,
                        CoffeeNoteActivity.newIntent(RuntimeEnvironment.application, id))
                        .create()
                        .resume()
                        .visible()
                        .get()

        assertThat((activity.findViewById(R.id.coffee_note_title) as EditText).text.toString())
                .isEqualTo(coffeeNote.title)
        assertThat((activity.findViewById(R.id.coffee_type_spinner) as Spinner).selectedItem)
                .isEqualTo(coffeeNote.coffeeType)
        assertThat((activity.findViewById(R.id.beans_name_text_view) as TextView).text.toString())
                .isEqualTo(beansType.name)
        assertThat(
                (activity.findViewById(R.id.beans_country_text_view) as TextView).text.toString())
                .isEqualTo(beansType.country)
        val beansTypeImageView =
                (activity.findViewById(R.id.beans_type_image_view) as ImageView)
        // TODO: find a way of comparing Bitmaps.
        assertThat((beansTypeImageView.drawable as BitmapDrawable).bitmap).isNotNull()
    }

    private fun createDefaultCoffeeNote(id: UUID, beansType: BeansType): CoffeeNote {
        return CoffeeNote(id, "Test title", CoffeeType.ESPRESSO, beansType.id)
    }

    private fun createDefaultBeansType(): BeansType {
        return BeansType(UUID.randomUUID(), "Name", "Country", 4)
    }
}