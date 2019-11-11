package co.windly.ktxaccount.sample.kotlindagger.presentation.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import co.windly.ktxaccount.sample.kotlindagger.R.layout
import co.windly.ktxaccount.sample.kotlindagger.utility.manager.AccountUtilityManager
import dagger.android.AndroidInjection
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MainActivity : Activity() {

  //region Companion

  companion object {
    const val TAG = "MainActivity"
  }

  //endregion

  //region Disposables

  private val disposables: CompositeDisposable
    by lazy { CompositeDisposable() }

  //endregion

  //region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {

    // Inject dependencies.
    AndroidInjection.inject(this)

    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    // Subscribe to changes.
    observeId()
    observeFirstName()
    observeLastName()
    observeAccessToken()
    observeRefreshToken()
    observeExpirationDate()

    // Initialize account.
    createAccount { fillAccountDetails() }
  }

  //endregion

  //region Initialize Account

  // Define account name (eg. based on email).
  val name = "john.snow@windly.co"

  @Inject
  lateinit var manager: AccountUtilityManager

  private fun createAccount(onComplete: () -> Unit) {

    // Create account.
    manager
      .saveAccount(name, "WinterIsComing")
      .doOnComplete(onComplete)
      .subscribe()
      .addTo(disposables)
  }

  private fun fillAccountDetails() {

    // Put a single value.
    manager
      .saveRxId(name, 1L)
      .subscribe()
      .addTo(disposables)

    // Put several values in chained stream.
    Completable
      .mergeArrayDelayError(
        manager.saveRxFirstName(name, "John"),
        manager.saveRxLastName(name, "Snow"),
        manager.saveRxAccessToken(name, "aXcacaaa12ssas1231l14k"),
        manager.saveRxRefreshToken(name, "bYdbdbbb23ttbt2342m25l"),
        manager.saveRxExpirationDate(name, 1573399868L)
      )
      .subscribe()
      .addTo(disposables)

    // Access properties one by one.
    manager
      .getRxId(name)
      .subscribe { id -> Log.d(TAG, "id -> $id.") }
      .addTo(disposables)

    manager
      .getRxFirstName(name)
      .subscribe { firstName ->
        Log.d(
          TAG, "first name -> $firstName.")
      }
      .addTo(disposables)

    manager
      .getRxLastName(name)
      .subscribe { lastName ->
        Log.d(
          TAG, "last name -> $lastName.")
      }
      .addTo(disposables)

    manager
      .getRxAccessToken(name)
      .subscribe { accessToken ->
        Log.d(
          TAG, "access name -> $accessToken.")
      }
      .addTo(disposables)

    manager
      .getRxRefreshToken(name)
      .subscribe { refreshToken ->
        Log.d(
          TAG, "refresh name -> $refreshToken.")
      }
      .addTo(disposables)

    manager
      .getRxExpirationDate(name)
      .subscribe { expirationDate ->
        Log.d(
          TAG, "expiration date -> $expirationDate.")
      }
      .addTo(disposables)

    // Remove a value.
    manager
      .removeRxFirstName(name)
      .subscribe()
      .addTo(disposables)

    // Clear all preferences.
    manager
      .clearRx(name)
      .subscribe()
      .addTo(disposables)

    // Remove account.
    manager
      .removeAccount(name)
      .subscribe { Log.d(TAG, "Account has been removed.") }
      .addTo(disposables)
  }

  //endregion

  //region Id

  private fun observeId() {

    // Subscribe to id changes.
    manager
      .observeRxId(name)
      .subscribe(
        { handleObserveRxIdSuccess(it) },
        { handleObserveRxIdError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxIdSuccess(id: Long) {

    // Log the fact.
    Log.d(TAG, "Id changed: $id.")
  }

  private fun handleObserveRxIdError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing id.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion

  //region First Name

  private fun observeFirstName() {

    // Subscribe to first name changes.
    manager
      .observeRxFirstName(name)
      .subscribe(
        { handleObserveRxFirstNameSuccess(it) },
        { handleObserveRxFirstNameError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxFirstNameSuccess(firstName: String) {

    // Log the fact.
    Log.d(TAG, "First name changed: $firstName.")
  }

  private fun handleObserveRxFirstNameError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing first name.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion

  //region Last Name

  private fun observeLastName() {

    // Subscribe to last name changes.
    manager
      .observeRxLastName(name)
      .subscribe(
        { handleObserveRxLastNameSuccess(it) },
        { handleObserveRxLastNameError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxLastNameSuccess(lastName: String) {

    // Log the fact.
    Log.d(TAG, "Last name changed: $lastName.")
  }

  private fun handleObserveRxLastNameError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing last name.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion

  //region Access Token

  private fun observeAccessToken() {

    // Subscribe to access token changes.
    manager
      .observeRxAccessToken(name)
      .subscribe(
        { handleObserveRxAccessTokenSuccess(it) },
        { handleObserveRxAccessTokenError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxAccessTokenSuccess(accessToken: String) {

    // Log the fact.
    Log.d(TAG, "Access token changed: $accessToken.")
  }

  private fun handleObserveRxAccessTokenError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing access token.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion

  //region Refresh Token

  private fun observeRefreshToken() {

    // Subscribe to refresh token changes.
    manager
      .observeRxRefreshToken(name)
      .subscribe(
        { handleObserveRxRefreshTokenSuccess(it) },
        { handleObserveRxRefreshTokenError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxRefreshTokenSuccess(refreshToken: String) {

    // Log the fact.
    Log.d(TAG, "Refresh token changed: $refreshToken.")
  }

  private fun handleObserveRxRefreshTokenError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing refresh token.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion

  //region Expiration Date

  private fun observeExpirationDate() {

    // Subscribe to expiration date changes.
    manager
      .observeRxExpirationDate(name)
      .subscribe(
        { handleObserveRxExpirationDateSuccess(it) },
        { handleObserveRxExpirationDateError(it) }
      )
      .addTo(disposables)
  }

  private fun handleObserveRxExpirationDateSuccess(expirationDate: Long) {

    // Log the fact.
    Log.d(TAG, "Expiration date changed: $expirationDate.")
  }

  private fun handleObserveRxExpirationDateError(throwable: Throwable) {

    // Log an error.
    Log.e(TAG, "An error occurred while observing expiration date.")
    Log.e(TAG, throwable.localizedMessage)
  }

  //endregion
}
