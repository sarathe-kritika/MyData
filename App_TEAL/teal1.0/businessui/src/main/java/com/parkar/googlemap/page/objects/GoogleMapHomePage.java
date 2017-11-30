package com.parkar.googlemap.page.objects;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseDragAndDrop;
import com.parkar.element.interfaces.IBaseElement;
import com.parkar.element.interfaces.IBaseTextBox;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.findbysImp.ParkarFindBy;

public class GoogleMapHomePage extends BaseUIPage {
	
	@ParkarFindBy(id = "searchboxinput", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox searchLocationTxtBx;
	
	@ParkarFindBy(id = "searchbox-searchbutton", via = ParkarLocateVia.ByElementClickable)
	public IBaseElement searchLocationBtn;
	
	
	@ParkarFindBy(xpath = "//div[@id='runway-expand-button']//button[@class='widget-expand-button-pegman-background grab-cursor']", via = ParkarLocateVia.ByElementClickable)
	public IBaseDragAndDrop pageManBtn;
	
	@ParkarFindBy(xpath = "//div[@id='scene']/div[3]/canvas", via = ParkarLocateVia.ByElementClickable)
	public IBaseElement googleMap;
	
	String pageManDropLocation="resources\\images\\PageManDropLocation.png";
	String pageMan="resources\\images\\PageMan.png";
	

	public GoogleMapHomePage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public GoogleSearchLocationResultPage searchLocation(String location) throws ParkarCoreCommonException{
		searchLocationTxtBx.setText(location);
		searchLocationBtn.click();
		return new GoogleSearchLocationResultPage(driver);
	}
	
	public void movePageManOnGoogleMap() throws Exception{
		Thread.sleep(5000);
		Pattern pageManDropLocationPattern = new Pattern(pageManDropLocation);
		Pattern pageManPattern = new Pattern(pageMan);
		Screen src = new Screen();
		src.setAutoWaitTimeout(30);
		src.find(pageManPattern).highlight(3);
		src.find(pageManDropLocationPattern).highlight(3);
		src.dragDrop(pageManPattern, pageManDropLocationPattern);
		src.setAutoWaitTimeout(30);
		Thread.sleep(10000);
		
	}
	
	

}
