package common;

import java.awt.Component;
import java.awt.Container;




/**
 *
 *
 *
 * */

public class FlexBox {
	public static final int ALIGMENT_CENTER = 1;
	public static final int ALIGMENT_PREVIOUS_ELEMENT = 2;
	public static final int ALIGMENT_WIDTH_PARENT = 3;
	public static final int ALIGNMENT_REMAINING_SIZE_OF_PANEL = 4;

	public static final int DIRECTION_COLUMN = 4;
	public static final int DIRECTION_ROW = 5;

	private int flag = 0;
	private int coordinateElementPrevious = 0;

	private int padding;
	private Container container;
	private int direction = FlexBox.DIRECTION_COLUMN;

	private int maxPercent = 100;

	public FlexBox(Container container, int direction) {
		this.container = container;
		
		if(direction < 4 || direction > 5) {
			this.direction = FlexBox.DIRECTION_COLUMN;
			return;
		}	
		
		this.direction = direction;
	}

	public void setPadding(int padding) {
		this.padding = padding;
		this.flag = padding;
	}

	/**
	 * Hàm set vị trí của component trong component cha
	 * @param comp Component cần set vị trí
	 * @param marginVertical set chi số margin của component so với 1 component khác, nếu
	 * direction là column thì marginVertical sẽ set so với component khác nằm ở trên đầu component này,
	 * @param marginHorizon set chỉ số margin của component so với 1 component khác, neeus
	 * */
	public void setPosition(Component comp, int marginVertical, int marginHorizon) {
		this.checkWidthAndHeight(comp);

		int x = 0, y = 0;

		int number;
		if(this.direction == FlexBox.DIRECTION_COLUMN) {
			if(comp.getHeight() > (number = container.getHeight() - flag - padding - marginVertical)) {
				comp.setSize(comp.getWidth(), number);
			}
			x = (comp.getWidth() == this.container.getWidth() - this.padding)? padding : padding + marginHorizon;
			y = flag + marginVertical;
		}
		else {
			if(comp.getWidth() > (number = container.getWidth() - flag - padding - marginHorizon)) {
				comp.setSize(number, comp.getHeight());
			}
			x = flag + marginHorizon;
			y = (comp.getHeight() == this.container.getHeight() - this.padding)? padding : padding + marginVertical;
		}


		this.updateVariableAndSetLocation(x, y, comp);
	}

	/**
	 *  
	 * */
	private int checkComponentSizeHasMaximum(int compNumber, int containNumber, int margin) {
		int numberCheck = containNumber - flag - padding - margin;
		if(compNumber > numberCheck) {
			return numberCheck;	
		}
		else {
			return -1;
		}
		
	}

	public void setPositionWithinPercentSize(int percent, int margin, Component comp) {
		int x = 0, y = 0;

		if(this.direction == FlexBox.DIRECTION_ROW) {
			int remainingWidth = (flag == 0)? container.getWidth() - padding * 2 : container.getWidth() - flag - margin - padding;
			// kiem tra xem container theo CHIEU NGANG con` khoang trong hay khong
			if(remainingWidth < 1) {
				return;
			}

			int widthWithinPercent = Util.calculatePrecent(percent, container.getWidth() - padding * 2);

			if(widthWithinPercent > remainingWidth) {
				comp.setSize(remainingWidth, container.getHeight() - padding * 2);
			}
			else {
				comp.setSize(widthWithinPercent, container.getHeight() - padding * 2);
			}

			x = flag + margin;
			y = padding;
		}	
		else {
			int remainingHeight = (flag == 0)? container.getHeight() - padding * 2 : container.getHeight() - flag - margin - padding;

			// kiem tra xem container theo CHIEU DOC con` khoang trong' hay khong
			if(remainingHeight < 1) {
				return;	
			} 
			int heightWithinPercent = Util.calculatePrecent(percent, container.getHeight());
			
			if(heightWithinPercent > remainingHeight) {
				comp.setSize(container.getWidth() - padding * 2, remainingHeight);
			}
			else {
				comp.setSize(container.getWidth() - padding * 2, heightWithinPercent);
			}

			x = padding;
			y = flag + margin;
		}

		this.updateVariableAndSetLocation(x, y, comp);
	}

	public void setPosition(int aligment, int marginVertical, Component comp) {
		this.checkWidthAndHeight(comp);
		
		int coordinate = 0;
		int x = 0, y = 0;

		switch(aligment) {
			case FlexBox.ALIGMENT_CENTER:
				int number = (this.direction == FlexBox.DIRECTION_COLUMN)? comp.getWidth() : comp.getHeight();
				coordinate = calculateCooradiateAligmentCenter(number);
				break;
			case FlexBox.ALIGMENT_PREVIOUS_ELEMENT:
				coordinate = this.coordinateElementPrevious;
				break;
			case FlexBox.ALIGMENT_WIDTH_PARENT:
				if(this.direction == FlexBox.DIRECTION_COLUMN) {
					comp.setSize(this.container.getWidth() - this.padding * 2, comp.getHeight());	
				}
				else {
					comp.setSize(comp.getWidth(), this.container.getHeight() - this.padding * 2);
				}
				coordinate = this.padding;
				break;
			case FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL:
				if(this.direction == FlexBox.DIRECTION_COLUMN) {
					comp.setSize(this.container.getWidth() - this.padding * 2, this.container.getHeight() - this.flag - this.padding - marginVertical);
					coordinate = this.padding;
				}

				break;
			default:
				this.setPosition(comp, marginVertical, 0);
				return;
		}

		if(this.direction == FlexBox.DIRECTION_COLUMN) {
			x = coordinate;
			y = flag + marginVertical;
		}
		else {
			y = coordinate;
			x = flag + marginVertical;
		}

		this.updateVariableAndSetLocation(x, y, comp);
	}

	private void updateVariableAndSetLocation(int x, int y, Component comp) {
		if(this.direction == FlexBox.DIRECTION_COLUMN) {
			this.flag = y + comp.getHeight();
			this.coordinateElementPrevious = x;
		}
		else {
			this.flag = x + comp.getWidth();
			this.coordinateElementPrevious = y;
		}

		comp.setLocation(x, y);	
		container.add(comp);
	}

	private void checkWidthAndHeight(Component comp) {
		int maxWidth = this.container.getWidth() - this.padding * 2;
		int maxHeight = this.container.getHeight() - this.padding * 2;

		if(comp.getWidth() > maxWidth) {
			comp.setSize(maxWidth, comp.getHeight());	
		}


		if(comp.getHeight() > maxHeight) {
			comp.setSize(comp.getWidth(), maxHeight);
		}
	}

	private int calculateCooradiateAligmentCenter(int number) {
		int numberCheck;

		if(this.direction == FlexBox.DIRECTION_COLUMN) {
			numberCheck = this.container.getWidth();
		}
		else {
			numberCheck = this.container.getHeight();
		}

		return (numberCheck == number)? 0 : (numberCheck - number) / 2;
	}

}
