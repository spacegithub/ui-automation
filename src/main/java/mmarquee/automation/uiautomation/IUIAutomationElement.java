package mmarquee.automation.uiautomation;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 * Created by HumphreysM on 06/07/2016.
 */
public interface IUIAutomationElement {

    /**
     * The interface IID for QueryInterface et al
     */
    public final static Guid.IID IID = new Guid.IID(
            "{D22108AA-8AC5-49A5-837B-37BBB3D7591E}");

    /**
     *
     * Retrieves pointers to the supported interfaces on an object.
     * This method calls IUnknown::AddRef on the pointer it returns.
     *
     * @param riid
     *            The identifier of the interface being requested.
     *
     * @param ppvObject
     *            The address of a pointer variable that receives the interface pointer requested in the riid parameter. Upon successful
     *            return, *ppvObject contains the requested interface pointer to the object. If the object does not support the
     *            interface, *ppvObject is set to NULL.
     *
     * @return
     *            This method returns S_OK if the interface is supported, and E_NOINTERFACE otherwise. If ppvObject is NULL, this method returns E_POINTER.
     *            For any one object, a specific query for the IUnknown interface on any of the object's interfaces must always return the same pointer value.
     *            This enables a client to determine whether two pointers point to the same component by calling QueryInterfacewith IID_IUnknown
     *            and comparing the results. It is specifically not the case that queries for interfaces other than IUnknown (even the same interface
     *            through the same pointer) must return the same pointer value.
     *
     *            There are four requirements for implementations of QueryInterface (In these cases, "must succeed" means "must succeed barring
     *            catastrophic failure."):
     *            The set of interfaces accessible on an object through QueryInterface must be static, not dynamic. This means that if a call
     *            toQueryInterface for a pointer to a specified interface succeeds the first time, it must succeed again, and if it fails
     *            the first time, it must fail on all subsequent queries. 
     *
     *            It must be reflexive: if a client holds a pointer to an interface on an object, and queries for that interface, the call must succeed. 
     *
     *            It must be symmetric: if a client holding a pointer to one interface queries successfully for another, a query through
     *            the obtained pointer for the first interface must succeed. 
     *
     *            It must be transitive: if a client holding a pointer to one interface queries successfully for a second, and through that
     *            pointer queries successfully for a third interface, a query for the first interface through the pointer for the
     *            third interface must succeed. 
     *            Notes to Implementers
     *            Implementations of QueryInterface must never check ACLs. The main reason for this rule is that COM requires that an object supporting a
     *            particular interface always return success when queried for that interface. Another reason is that checking ACLs on QueryInterface
     *            does not provide any real security because any client who has access to a particular interface can hand it directly to another
     *            client without any calls back to the server. Also, because COM caches interface pointers, it does not callQueryInterface on
     *            the server every time a client does a query.
     */
    WinNT.HRESULT QueryInterface(
            Guid.REFIID riid,
            PointerByReference ppvObject);

    /**
     *
     * Increments the reference count for an interface on an object. This method should be called for every new copy of a pointer to an interface on an object.
     * @return
     *            The method returns the new reference count. This value is intended to be used only for test purposes.
     *
     *            Objects use a reference counting mechanism to ensure that the lifetime of the object includes the lifetime of references to it. You use AddRef
     *            to stabilize a copy of an interface pointer. It can also be called when the life of a cloned pointer must extend beyond the
     *            lifetime of the original pointer. The cloned pointer must be released by calling IUnknown::Release.
     *
     *            The internal reference counter that AddRef maintains should be a 32-bit unsigned integer.
     *            Notes to Callers
     *            Call this method for every new copy of an interface pointer that you make. For example, if you are passing a copy of a pointer
     *            back from a method, you must call AddRef on that pointer. You must also call AddRef on a pointer before passing it as an in-out
     *            parameter to a method; the method will call IUnknown::Release before copying the out-value on top of it.
     */
    int AddRef();

    /**
     * Decrements the reference count for an interface on an object.
     *
     * @return
     *            The method returns the new reference count. This value is intended to be used only for test purposes.
     *
     *            When the reference count on an object reaches zero, Release must cause the interface pointer to free itself. When the released
     *            pointer is the only existing reference to an object (whether the object supports single or multiple interfaces), the
     *            implementation must free the object.
     *
     *            Note that aggregation of objects restricts the ability to recover interface pointers.
     *            Notes to Callers
     *            Call this method when you no longer need to use an interface pointer. If you are writing a method that takes an in-out
     *            parameter, call Release on the pointer you are passing in before copying the out-value on top of it.
     */
    int Release();

    int setFocus();
    int get_CurrentName (/* [retval][out] */ PointerByReference sr);
    int get_CurrentClassName (/* [retval][out] */ PointerByReference sr);
    int findAll (TreeScope scope, Pointer condition, /* [retval][out] */ PointerByReference sr);
    int findFirst (TreeScope scope, Pointer condition, /* [retval][out] */ PointerByReference sr);
    int get_ClickablePoint(WinDef.POINT clickable, IntByReference ok);
    int get_CurrentIsPassword(IntByReference value);
    int get_CurrentAriaRole (/* [retval][out] */ PointerByReference sr);
    int get_CurrentPattern(Integer patternId, PointerByReference pbr);
    int get_CurrentPropertyValue(int propertyId, Variant.VARIANT.ByReference value);
    int get_CurrentControlType(IntByReference ipr);

    public static class Converter {
        private static int UIAutomationElement_Methods  = 85; // 0-2 IUnknown, 3-85 IUIAutomationElement

        public static IUIAutomationElement PointerToInterface(final PointerByReference ptr) {
            final Pointer interfacePointer = ptr.getValue();
            final Pointer vTablePointer = interfacePointer.getPointer(0);
            final Pointer[] vTable = new Pointer[UIAutomationElement_Methods];
            vTablePointer.read(0, vTable, 0, vTable.length);
            return new IUIAutomationElement() {

                // IUnknown
                public WinNT.HRESULT QueryInterface(Guid.REFIID byValue, PointerByReference pointerByReference) {
                    Function f = Function.getFunction(vTable[0], Function.ALT_CONVENTION);
                    return new WinNT.HRESULT(f.invokeInt(new Object[]{interfacePointer, byValue, pointerByReference}));
                }

                public int AddRef() {
                    Function f = Function.getFunction(vTable[1], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer});
                }

                public int Release() {
                    Function f = Function.getFunction(vTable[2], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer});
                }

                public int setFocus() {
                    Function f = Function.getFunction(vTable[3], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer});
                }

                public int findFirst(TreeScope scope, Pointer condition, /* [retval][out] */ PointerByReference sr) {
                    Function f = Function.getFunction(vTable[5], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, scope.value, condition, sr});
                }

                public int findAll(TreeScope scope, Pointer condition, /* [retval][out] */ PointerByReference sr) {
                    Function f = Function.getFunction(vTable[6], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, scope.value, condition, sr});
                }

                public int get_CurrentPropertyValue(int propertyId, Variant.VARIANT.ByReference value) {
                    Function f = Function.getFunction(vTable[10], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, propertyId, value});
                }

                public int get_CurrentPattern(Integer patternId, PointerByReference pbr) {
                    Function f = Function.getFunction(vTable[16], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, patternId, pbr});
                }

                public int get_CurrentControlType(IntByReference ipr) {
                    Function f = Function.getFunction(vTable[21], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, ipr});
                }

                public /* [propget] */ int get_CurrentName(/* [retval][out] */ PointerByReference sr) {
                    Function f = Function.getFunction(vTable[23], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, sr});
                }

                public /* [propget] */ int get_CurrentClassName(/* [retval][out] */ PointerByReference sr) {
                    Function f = Function.getFunction(vTable[30], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, sr});
                }

                public int get_CurrentIsPassword(IntByReference value) {
                    Function f = Function.getFunction(vTable[35], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, value});
                }

                public int get_CurrentAriaRole(/* [retval][out] */ PointerByReference sr) {
                    Function f = Function.getFunction(vTable[45], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, sr});
                }

                public int get_ClickablePoint(WinDef.POINT clickable, IntByReference ok) {
                    Function f = Function.getFunction(vTable[52], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, clickable, ok});
                }
            };
        }
    }
}
