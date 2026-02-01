package me.twisti.mods.chatOpen.client.mixin

import me.twisti.mods.chatOpen.client.KeepChatOpenState
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.ChatScreen
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.KeyEvent
import net.minecraft.network.chat.Component
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable


@Mixin(ChatScreen::class)
abstract class ChatScreenMixin(component: Component) : Screen(component) {
    @Shadow
    protected var input: EditBox? = null

    @Shadow
    abstract fun handleChatInput(chatText: String, addToHistory: Boolean)

    @Inject(method = ["keyPressed"], at = [At("HEAD")], cancellable = true)
    private fun keepOpenOnEnter(keyEvent: KeyEvent, cir: CallbackInfoReturnable<Boolean>) {
        if (!KeepChatOpenState.enabled) return

        if (keyEvent.isConfirmation) {
            val text = this.input!!.value
            this.handleChatInput(text, true)
            this.input!!.value = ""

            this.minecraft.setScreen(this)
            cir.setReturnValue(true)
        }
    }
}