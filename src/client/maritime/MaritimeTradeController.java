package client.maritime;

import shared.definitions.*;
import client.base.*;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {

		System.out.println("i start the trade");
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {
		System.out.println("i make the trade");
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		System.out.println("i cancel the trade");
		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		System.out.println("This is the type "+resource.toString());

	}

	@Override
	public void setGiveResource(ResourceType resource) {
		System.out.println("This is the type "+resource.toString());

	}

	@Override
	public void unsetGetValue() {

	}

	@Override
	public void unsetGiveValue() {

	}

}

