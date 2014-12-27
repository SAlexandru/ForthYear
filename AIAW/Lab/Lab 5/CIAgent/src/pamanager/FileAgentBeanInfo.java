package pamanager;

import java.beans.*;


/**
 * The <code>FileAgentBeanInfo</code> class implements the bean info for
 * file agent bean.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class FileAgentBeanInfo extends SimpleBeanInfo {
  Class beanClass = FileAgent.class;
  String iconColor16x16Filename;
  String iconColor32x32Filename;
  String iconMono16x16Filename;
  String iconMono32x32Filename;
  private final static Class customizerClass = pamanager.FileAgentCustomizer.class;


  /**
   * Creates a <code>FileAgentBeanInfo</code> object.
   *
   */
  public FileAgentBeanInfo() {}


  /**
   * Retrieves a descriptor for this bean.
   *
   * @return    the bean descriptor which specifies a customizer for this
   *            bean class
   */
  public BeanDescriptor getBeanDescriptor() {
    BeanDescriptor returnDescriptor = new BeanDescriptor(beanClass, customizerClass);

    returnDescriptor.setValue("DisplayName", "FileAgent");
    return returnDescriptor;
  }


  /**
   * Retrieves the property descriptors for the file agent bean.
   *
   * @return the PropertyDescriptor[] object that contains the descriptors
   *
   */
  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor _action = new PropertyDescriptor("action", beanClass, "getAction", "setAction");
      PropertyDescriptor _actionString = new PropertyDescriptor("actionString", beanClass, "getActionString", "setActionString");
      PropertyDescriptor _condition = new PropertyDescriptor("condition", beanClass, "getCondition", "setCondition");
      PropertyDescriptor _fileName = new PropertyDescriptor("fileName", beanClass, "getFileName", "setFileName");
      PropertyDescriptor _parms = new PropertyDescriptor("parms", beanClass, "getParms", "setParms");
      PropertyDescriptor _taskDescription = new PropertyDescriptor("taskDescription", beanClass, "getTaskDescription", null);
      PropertyDescriptor _threshold = new PropertyDescriptor("threshold", beanClass, "getThreshold", "setThreshold");
      PropertyDescriptor[] pds = new PropertyDescriptor[] {
        _action, _actionString, _condition, _fileName, _parms,
        _taskDescription, _threshold,
      };

      return pds;
    } catch (IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }


  /**
   * Retrieves the icon for the bean info.
   *
   * @param iconKind the integer that indicates the kind of icon to be
   *        retrieved
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
   * @return the BeanInfo[] object that contains bean info from the super
   *         class
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
