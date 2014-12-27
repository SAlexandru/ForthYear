package ciagent;

import ciagent.CIAgent;

import java.beans.*;


/**
 * The <code>CIAgentBeanInfo</code> class defines the information available
 * for an agent bean.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P.Bigus and Jennifer Bigus 2001
 *
 */
public class CIAgentBeanInfo extends SimpleBeanInfo {
  Class beanClass = CIAgent.class;
  String iconColor16x16Filename;
  String iconColor32x32Filename;
  String iconMono16x16Filename;
  String iconMono32x32Filename;


  /**
   * Creates a CIAgentBeanInfo object.
   */
  public CIAgentBeanInfo() {}


  /**
   * Retrieves the property descriptoprs for the bean.
   *
   * @return an array of property descriptors
   */
  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor _name = new PropertyDescriptor("name", beanClass, "getName", "setName");
      PropertyDescriptor _state = new PropertyDescriptor("state", beanClass, "getState", null);
      PropertyDescriptor[] pds = new PropertyDescriptor[]{ _name, _state };

      return pds;
    } catch (IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }


  /**
   * Retrieves the icon image for the bean.
   *
   * @param iconKind the integer for the type of icon to be retrieved
   *
   * @return the image for the icon
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
   * Retrieves additional bean information.
   *
   * @return an array of <code>BeanInfo</code> objects
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
