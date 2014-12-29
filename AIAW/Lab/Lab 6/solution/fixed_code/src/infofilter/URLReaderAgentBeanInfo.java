package infofilter;

import java.beans.*;


/**
 * The <code>URLReaderAgentBeanInfo</code> class implements the bean info for
 * the URL reader agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class URLReaderAgentBeanInfo extends SimpleBeanInfo {
  Class<URLReaderAgent> beanClass = URLReaderAgent.class;
  String iconColor16x16Filename;
  String iconColor32x32Filename;
  String iconMono16x16Filename;
  String iconMono32x32Filename;
  private final static Class<URLReaderAgentCustomizer> customizerClass = infofilter.URLReaderAgentCustomizer.class;


  /**
   * Creates a <code>URLReaderAgentBeanInfo</code> object.
   *
   */
  public URLReaderAgentBeanInfo() {}


  /**
   * Retrieves a descriptor for this bean.
   *
   * @return  a bean descriptor which specifies a customizer for this
   *          bean class
   */
  public BeanDescriptor getBeanDescriptor() {
    BeanDescriptor returnDescriptor = new BeanDescriptor(beanClass, customizerClass);

    returnDescriptor.setValue("DisplayName", "URLReaderAgent");
    return returnDescriptor;
  }


  /**
   * Retrieves the property descriptors for the bean.
   *
   * @return the PropertyDescriptor[] object that contains the property
   *         descriptors
   *
   */
  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor _url = new PropertyDescriptor("url", beanClass, "getURL", "setURL");
      PropertyDescriptor _paramString = new PropertyDescriptor("paramString", beanClass, "getParamString", "setParamString");
      PropertyDescriptor _contents = new PropertyDescriptor("contents", beanClass, "getContents", null);
      PropertyDescriptor _taskDescription = new PropertyDescriptor("taskDescription", beanClass, "getTaskDescription", null);
      PropertyDescriptor[] pds = new PropertyDescriptor[]{ _url,
                                                           _paramString,
                                                           _contents,
                                                           _taskDescription, };

      return pds;
    } catch (IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }


  /**
   * Retrieves the icon.
   *
   * @param iconKind the integer that represents the icon kind
   *
   * @return the java.awt.Image object for the icon
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
   * @return the BeanInfo[] object that contains the superclass bean info
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
