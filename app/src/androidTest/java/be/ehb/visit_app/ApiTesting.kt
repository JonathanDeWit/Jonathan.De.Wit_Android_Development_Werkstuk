package be.ehb.visit_app

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.ehb.visit_app.Models.ApiCall
import be.ehb.visit_app.Models.City
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ApiTesting {
    var queue = Volley.newRequestQueue(InstrumentationRegistry.getInstrumentation().targetContext)

    //Test if the City object contains the expected information of the requested city name
    @Test
    fun findCityTestA() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        var cityName = "Brussel"
        var city: City

        runBlocking {
            withContext(Dispatchers.IO){
                city = ApiCall.getCity(cityName, "Testing_Request", queue)
            }
        }

        assertEquals("Brussel", city.name)
        assertEquals("BE", city.country)
    }
    //Test if the City object contains a diffrent information than the requested city name
    @Test
    fun findCityTestB() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        var cityName = "Paris"
        var city: City

        runBlocking {
            withContext(Dispatchers.IO){
                city = ApiCall.getCity(cityName, "Testing_Request", queue)
            }
        }

        assertNotEquals("Brussel", city.name)
        assertNotEquals("BE", city.country)
    }
}