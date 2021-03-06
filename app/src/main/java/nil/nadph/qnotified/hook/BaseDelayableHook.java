package nil.nadph.qnotified.hook;

import nil.nadph.qnotified.SyncUtils;
import nil.nadph.qnotified.config.SwitchConfigItem;
import nil.nadph.qnotified.util.DexKit;

public abstract class BaseDelayableHook implements SwitchConfigItem {

    private static BaseDelayableHook[] sAllHooks;

    private int myId = -1;

    public static BaseDelayableHook getHookByType(int hookId) {
        return queryDelayableHooks()[hookId];
    }

    public static BaseDelayableHook[] queryDelayableHooks() {
        if (sAllHooks == null) sAllHooks = new BaseDelayableHook[]{
                SettingEntryHook.get(),
                DelDetectorHook.get(),
                PttForwardHook.get(),
                MuteAtAllAndRedPacket.get(),
                CardMsgHook.get(),
                FlashPicHook.get(),
                RepeaterHook.get(),
                EmoPicHook.get(),
                //GalleryBgHook.get(),
                FavMoreEmo.get(),
                RevokeMsgHook.get(),
                FakeVipHook.get(),
                HideGiftAnim.get(),
                SimpleCheckInHook.get(),
                PreUpgradeHook.get(),
                CheatHook.get(),
                RoundAvatarHook.get(),
                $endGiftHook.get(),
                MultiForwardAvatarHook.get(),
                ReplyNoAtHook.get(),
                MuteQZoneThumbsUp.get(),
                FakeBatteryHook.get(),
                FileRecvRedirect.get(),
                ShowPicGagHook.get(),
                DefaultBubbleHook.get()
        };
        return sAllHooks;
    }

    public boolean isTargetProc() {
        return (getEffectiveProc() & SyncUtils.getProcessType()) != 0;
    }

    public abstract int getEffectiveProc();

    public abstract boolean isInited();

    public abstract boolean init();

    public abstract int[] getPreconditions();

    @Override
    public boolean isValid() {
        return true;
    }

    public boolean checkPreconditions() {
        for (int i : getPreconditions()) {
            if (DexKit.tryLoadOrNull(i) == null) return false;
        }
        return true;
    }

    public int getId() {
        if (myId != -1) return myId;
        BaseDelayableHook[] hooks = queryDelayableHooks();
        for (int i = 0; i < hooks.length; i++) {
            if (hooks[i].getClass().equals(this.getClass())) {
                myId = i;
                return myId;
            }
        }
        return -1;
    }

    public abstract boolean isEnabled();

    /**
     * This method must be safe to call even it is NOT inited
     *
     * @param enabled has no effect if isValid() returns false
     */
    @Override
    public abstract void setEnabled(boolean enabled);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + (isInited() ? "inited" : "") + "," + (isEnabled() ? "enabled" : "") + "," + SyncUtils.getProcessName() + ")";
    }

}
