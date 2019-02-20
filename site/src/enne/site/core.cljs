(ns enne.site.core
  (:require [reagent.core :as reagent]
            [enne.site.db :as db]))


(defn <person>
  []
  [:section.section
   [:div.container
    [:div.box
     [:h4.title.is-4
      [:span (db/person-name)]
      [:span.has-text-grey " · " (db/municipality)]]
     [:p.subtitle
      [:span.is-family-monospace.has-text-info
       (db/personal-identity-code)]]]]])


(defn <year>
  [error? bound]
  [:input.input
   {:type      :number
    :step      1
    :min       1800
    :max       2999
    :value     (db/year bound)
    :on-change (partial db/set-year bound)
    :class     (when error? :is-danger)}])


(defn <years>
  [error?]
  [:div.field
   [:label.label
    "Year of birth"]
   [:div.field-body
    [:div.field.is-narrow
     [:p.control
      [<year> error? :lower-bound]]]
    [:div.field.is-narrow
     [:p.control
      [<year> error? :upper-bound]]]]])


(defn <sex>
  []
  [:div.field
   [:label.label
    "Sex"]
   [:div.field-body
    [:div.field.is-narrow
     [:div.control
      [:label.radio
       [:input {:type            :radio
                :name            :sex
                :default-checked true
                :on-click        #(db/set-sex :sex/female)}]
       " Female"]
      [:label.radio
       [:input {:type     :radio
                :name     :sex
                :on-click #(db/set-sex :sex/male)}]
       " Male"]]]]])


(defn <form>
  []
  (let [lower-bound (get-in @db/db [:year :lower-bound])
        upper-bound (get-in @db/db [:year :upper-bound])
        error?      (or (< upper-bound lower-bound) (> upper-bound 2999) (< lower-bound 1800))]
    [:section.section
     [:div.container
      [:div.columns
       [:div.column.is-narrow
        [<years> error?]
        [:div.field
         (when (< upper-bound lower-bound)
           [:p.help.is-danger
            "The upper bound must be greater than or equal to the lower bound."])
         (when (> upper-bound 2999)
           [:p.help.is-danger
            (str "The upper bound cannot be greater than 2999.")])
         (when (< lower-bound 1800)
           [:p.help.is-danger
            (str "The lower bound cannot be less than 1800.")])]]
       [:div.column.is-narrow
        [<sex>]]]
      [:div.field
       [:div.field-body
        [:div.field
         [:div.control
          [:div.buttons
           [:button.button.is-primary
            {:on-click #(db/make-person)
             :disabled error?}
            "Make a Finn"]]]]]]]]))


(defn <ui>
  []
  [:<>
   [<person>]
   [<form>]])


(defn start
  []
  (reagent/render [<ui>] (.getElementById js/document "app")))


(start)
