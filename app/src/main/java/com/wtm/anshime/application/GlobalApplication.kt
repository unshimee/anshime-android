package com.wtm.anshime.application

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.wtm.anshime.utils.Constants.KAKAO_NATIVE_APP_KEY

class GlobalApplication : Application() {

  override fun onCreate() {
      super.onCreate()
      KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
  }
}