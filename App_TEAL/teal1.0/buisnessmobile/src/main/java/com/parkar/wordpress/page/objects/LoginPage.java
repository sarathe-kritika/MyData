package com.parkar.wordpress.page.objects;

import io.appium.java_client.AppiumDriver;

import com.parkar.basepageobject.BaseMobilePage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseTextBox;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreMobileException;
import com.parkar.findbysImp.ParkarFindBy;

public class LoginPage extends BaseMobilePage{

	public LoginPage(AppiumDriver<?> driver) throws ParkarCoreCommonException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@ParkarFindBy(iosClassName="UIATextField", androidId="org.wordpress.android:id/nux_username")
	IBaseTextBox USERNAME_TXT_BX;
	
	@ParkarFindBy(iosClassName="UIASecureTextField", androidId="org.wordpress.android:id/nux_password", via=ParkarLocateVia.ByElementPresent, timeout=70)
	IBaseTextBox PASSWORD_TXT_BX;
	
	@ParkarFindBy(iosId="Sign In", androidId="org.wordpress.android:id/nux_sign_in_button", via=ParkarLocateVia.ByElementPresent, timeout=80)
	IBaseButton SIGN_IN_BTN;
	
	public void loginApp(String userName, String password) throws ParkarCoreMobileException {
		USERNAME_TXT_BX.setText(userName);
		PASSWORD_TXT_BX.setText(password);
		SIGN_IN_BTN.click();
	}

}
