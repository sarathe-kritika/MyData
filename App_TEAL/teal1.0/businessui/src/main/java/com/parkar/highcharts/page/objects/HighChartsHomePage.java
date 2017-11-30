package com.parkar.highcharts.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseLabel;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;
import com.parkar.helpers.BasicPageElementHelper;

public class HighChartsHomePage extends BaseUIPage{
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='text'  and @class='highcharts-title']//*[local-name()='tspan']", via = ParkarLocateVia.ByElementVisible)
	public  IBaseLabel averageTempLbl;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='text'  and @class='highcharts-subtitle']//*[local-name()='tspan']", via = ParkarLocateVia.ByElementVisible)
	public IBaseLabel sourceLbl;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='g'  and @class='highcharts-button']", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton menuBtn;
	
	@ParkarFindBy(xpath = "//div[@id='highcharts-0']/div/div/div[2]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink downloadLnk;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='g' and @class='highcharts-legend']//*[name()='g' and @class='highcharts-legend-item']//*[name()='path' and @fill='#434348']", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink newyorkLnk;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='g' and @class='highcharts-legend']//*[name()='g' and @class='highcharts-legend-item']//*[name()='path' and @fill='#90ed7d']", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink berlinLnk;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='g' and @class='highcharts-legend']//*[name()='g' and @class='highcharts-legend-item']//*[name()='path' and @fill='#7cb5ec']", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink tokyoLnk;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='g' and @class='highcharts-legend']//*[name()='g' and @class='highcharts-legend-item']//*[name()='path' and @fill='#f7a35c']", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink londonLnk;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='g' and @class='highcharts-tooltip']//*[name()='text']//*[local-name()='tspan'][4]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLabel tempToolTipLbl;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='g' and @class='highcharts-tooltip']//*[name()='text']//*[local-name()='tspan'][1]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLabel monthToolTipLbl;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='g' and @class='highcharts-tooltip']//*[name()='text']//*[local-name()='tspan'][3]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLabel cityToolTipLbl;
	
	@ParkarFindBy(xpath = "//*[name()='svg']//*[name()='g'][5]//*[name()='g'][4]//*[name()='path'][1]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLabel toolTipLbl;
	
	
	public IBaseLink getPlotGraphLink(int i) throws ParkarCoreUIException{
		String locator ="//*[name()='svg']//*[name()='g' and @class='highcharts-legend']//*[name()='g' and @class='highcharts-legend-item']["+i+"]";
		return initElement(locator, ParkarLocateVia.ByElementClickable);
	}
	public HighChartsHomePage(WebDriver driver)
			throws ParkarCoreCommonException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void downloadGraph() throws ParkarCoreUIException{
		//BasicPageElementHelper.executeScript(driver, "scroll(0,-250);");
		menuBtn.click();
		try {
			Thread.sleep(5000);
			downloadLnk.click();
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeTokyoGraph() throws ParkarCoreUIException{
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BasicPageElementHelper.executeScript(driver, "scroll(0,-250);");
		tokyoLnk.click();
	}
	
	public void removeLondonGraph() throws ParkarCoreUIException{
		londonLnk.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void plotGraphAgain() throws ParkarCoreUIException{
		for(int i=1;i<5;i++)
			getPlotGraphLink(i).click();
		
	}
	
	public void removeNewYorkGraph() throws ParkarCoreUIException{
		newyorkLnk.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeBerlinGraph() throws ParkarCoreUIException{
		berlinLnk.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void hoverDataPoints() throws ParkarCoreCommonException{
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toolTipLbl.hover();
     }

}
