package pamanager;

import java.beans.*;


/**
 * The <code>AirfareAgentBeanInfo</code> class implements the bean info
 * for the Airfare Agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class AirfareAgentBeanInfo extends SimpleBeanInfo {
  Class<AirfareAgent> beanClass = AirfareAgent.class;
  String iconColor16x16Filename;
  String iconColor32x32Filename;
  String iconMono16x16Filename;
  String iconMono32x32Filename;
  private final static Class<AirfareAgentCustomizer> customizerClass = pamanager.AirfareAgentCustomizer.class;


  /**
   * Creates a <code>AirfareAgentBeanInfo</code> object.
   *
   */
  public AirfareAgentBeanInfo() {}


  /**
   * Retrieves a descriptor for this bean.
   *
   * @return    the bean descriptor which specifies a customizer for this
   *            bean class.
   */
  public BeanDescriptor getBeanDescriptor() {
    BeanDescriptor returnDescriptor = new BeanDescriptor(beanClass, customizerClass);

    returnDescriptor.setValue("DisplayName", "AirfareAgent");
    return returnDescriptor;
  }


  /**
   * Retrieves the property descriptors for this bean.
   *
   * @return the PropertyDescriptor[] objects
   *
   */
  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor _departMonth = new PropertyDescriptor("departMonth", beanClass, "getDepartMonth", "setDepartMonth");
      PropertyDescriptor _departDay = new PropertyDescriptor("departDay", beanClass, "getDepartDay", "setDepartDay");
      PropertyDescriptor _origCity = new PropertyDescriptor("origCity", beanClass, "getOrigCity", "setOrigCity");
      PropertyDescriptor _destCity = new PropertyDescriptor("destCity", beanClass, "getDestCity", "setDestCity");
      PropertyDescriptor _returnMonth = new PropertyDescriptor("returnMonth", beanClass, "getReturnMonth", "setReturnMonth");
      PropertyDescriptor _returnDay = new PropertyDescriptor("returnDay", beanClass, "getReturnDay", "setReturnDay");
      PropertyDescriptor _taskDescription = new PropertyDescriptor("taskDescription", beanClass, "getTaskDescription", null);
      PropertyDescriptor[] pds = new PropertyDescriptor[] {
        _departMonth,
        _departDay,
        _origCity,
        _destCity,
        _returnMonth,
        _returnDay,
        _taskDescription,
      };

      return pds;
    } catch (IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }


  /**
   * Retrieves the icon for the bean.
   *
   * @param iconKind the integer that represents the icon kind
   *
   * @return the java.awt.Image object that contains the icon image
   *
   */
  public java.awt.Image getIcon(int iconKind) {
    switch (iconKind) {
      case BeanInfo.ICON_COLOR_16x16 :
        return (iconColor16x16Filename != null)
               ? loadImage(iconColor16x16Filename)
               : null;
      case BeanInfo.ICON_COLOR_32x32 :
        return (iconColor32x32Filename != null)
               ? loadImage(iconColor32x32Filename)
               : null;
      case BeanInfo.ICON_MONO_16x16 :
        return (iconMono16x16Filename != null)
               ? loadImage(iconMono16x16Filename)
               : null;
      case BeanInfo.ICON_MONO_32x32 :
        return (iconMono32x32Filename != null)
               ? loadImage(iconMono32x32Filename)
               : null;
    }
    return null;
  }


  /**
   * Retrieves additional bean info from the superclass.
   *
   * @return the BeanInfo[] object that contains the additional bean info
   *
   */
  public BeanInfo[] getAdditionalBeanInfo() {
    Class<?> superclass = beanClass.getSuperclass();

    try {
      BeanInfo superBeanInfo = Introspector.getBeanInfo(superclass);

      return new BeanInfo[]{ superBeanInfo };
    } catch (IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
