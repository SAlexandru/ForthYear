package pamanager;

import java.beans.*;


/**
 * The <code>SchedulerAgentBeanInfo</code> class implements the bean info
 * for the scheduler agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class SchedulerAgentBeanInfo extends SimpleBeanInfo {
  Class beanClass = SchedulerAgent.class;
  String iconColor16x16Filename;
  String iconColor32x32Filename;
  String iconMono16x16Filename;
  String iconMono32x32Filename;
  private final static Class customizerClass = pamanager.SchedulerAgentCustomizer.class;


  /**
   * Creates a <code>SchedulerAgentBeanInfo</code> object.
   *
   */
  public SchedulerAgentBeanInfo() {}


  /**
   * Retrieves a descriptor for this bean.
   *
   * @return    the bean descriptor which specifies a customizer for this
   *            bean class
   */
  public BeanDescriptor getBeanDescriptor() {
    BeanDescriptor returnDescriptor = new BeanDescriptor(beanClass, customizerClass);

    returnDescriptor.setValue("DisplayName", "SchedulerAgent");
    return returnDescriptor;
  }


  /**
   * Retrieves the property desriptors for this bean.
   *
   * @return the PropertyDescriptor[] object that contains a set of
   *         property descriptors for the SchedulerAgent bean
   *
   */
  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor _interval = new PropertyDescriptor("interval", beanClass, "getInterval", "setInterval");
      PropertyDescriptor _oneShot = new PropertyDescriptor("oneShot", beanClass, "getOneShot", "setOneShot");
      PropertyDescriptor _time = new PropertyDescriptor("time", beanClass, "getTime", "setTime");
      PropertyDescriptor _actionString = new PropertyDescriptor("actionString", beanClass, "getActionString", "setActionString");
      PropertyDescriptor[] pds = new PropertyDescriptor[] {
        _interval, _oneShot, _time, _actionString
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
   * @param iconKind the integer that represents the kind of icon to be
   *                 retrieved
   *
   * @return the java.awt.Image object that contains the icon
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
   * @return the BeanInfo[] object that contains information about the
   *         superclass
   *
   */
  public BeanInfo[] getAdditionalBeanInfo() {
    Class superclass = beanClass.getSuperclass();

    try {
      BeanInfo superBeanInfo = Introspector.getBeanInfo(superclass);

      return new BeanInfo[]{ superBeanInfo };
    } catch (IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
